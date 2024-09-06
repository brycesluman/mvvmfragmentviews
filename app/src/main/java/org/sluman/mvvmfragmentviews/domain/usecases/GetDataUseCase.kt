package org.sluman.mvvmfragmentviews.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sluman.mvvmfragmentviews.data.Resource
import org.sluman.mvvmfragmentviews.domain.CountryDomainEntity
import org.sluman.mvvmfragmentviews.domain.WalmartRepository

class GetDataUseCase(
    private val repository: WalmartRepository

) {
    operator fun invoke(): Flow<Resource<List<CountryDomainEntity>>> {
        return flow {
            repository.getData().collect {
                emit(it)
            }
        }
    }
}