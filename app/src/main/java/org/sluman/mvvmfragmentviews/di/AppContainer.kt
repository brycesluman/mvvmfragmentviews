package org.sluman.mvvmfragmentviews.di

import android.content.Context
import org.sluman.mvvmfragmentviews.data.WalmartRepositoryImpl
import org.sluman.mvvmfragmentviews.data.network.ApiClient
import org.sluman.mvvmfragmentviews.domain.usecases.GetDataUseCase
import org.sluman.mvvmfragmentviews.domain.usecases.SearchUseCase

class AppContainer(context: Context) {
    private val apiClient = ApiClient(context)
    private val walmartRepository = WalmartRepositoryImpl(apiClient)
    val getDataUseCase = GetDataUseCase(walmartRepository)
    val searchUseCase = SearchUseCase(walmartRepository)
}