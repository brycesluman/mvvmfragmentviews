package org.sluman.mvvmfragmentviews.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sluman.mvvmfragmentviews.App
import org.sluman.mvvmfragmentviews.data.MainState
import org.sluman.mvvmfragmentviews.data.Resource
import org.sluman.mvvmfragmentviews.domain.usecases.GetDataUseCase
import org.sluman.mvvmfragmentviews.domain.usecases.SearchUseCase


class HomeViewModel (
    private val useCase: GetDataUseCase,
    private val searchUseCase: SearchUseCase
) : ViewModel() {
    private var loadDataJob: Job? = null
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    var scrollPosition = 0

    init {
        getData()
    }

    fun getData() {
        loadDataJob?.cancel()
        loadDataJob = viewModelScope.launch {
            useCase.invoke().collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(dataItems = resultState.data,
                                isLoading = false)
                        }
                    }
                    is Resource.Error -> _state.update { it.copy(error = resultState.message,
                        isLoading = false) }
                    Resource.Loading -> _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    fun search(query: String) {
        loadDataJob?.cancel()
        loadDataJob = viewModelScope.launch(Dispatchers.IO) {
            searchUseCase.invoke(query).debounce(500L).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(dataItems = result.data,
                                isLoading = false)
                        }
                    }
                    is Resource.Error -> _state.update { it.copy(error = result.message,
                        isLoading = false) }
                    Resource.Loading -> _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val dataUseCase = (this[APPLICATION_KEY] as App).appContainer.getDataUseCase
                val searchUseCase = (this[APPLICATION_KEY] as App).appContainer.searchUseCase
                HomeViewModel(
                    useCase = dataUseCase,
                    searchUseCase = searchUseCase
                )
            }
        }
    }
}