package org.sluman.mvvmfragmentviews.domain.usecases

import android.text.TextUtils.substring
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sluman.mvvmfragmentviews.data.Resource
import org.sluman.mvvmfragmentviews.domain.CountryDomainEntity
import org.sluman.mvvmfragmentviews.domain.WalmartRepository
import java.util.Locale

class SearchUseCase(private val repository: WalmartRepository) {
    operator fun invoke(query: String): Flow<Resource<List<CountryDomainEntity>>> {
        return flow {
            repository.getData().collect {
                when (it) {
                    is Resource.Success -> {
                        val data = it.data.filter { item ->
                            query == substring(item.name.lowercase(Locale.ROOT), 0, query.length)
                        }
                        emit(Resource.Success(data))
                    }
                    is Resource.Error ->  Resource.Error(it.message)
                    Resource.Loading -> Resource.Loading
                }
            }
        }
    }
}