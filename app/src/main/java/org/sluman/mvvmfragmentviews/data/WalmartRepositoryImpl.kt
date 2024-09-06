package org.sluman.mvvmfragmentviews.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sluman.mvvmfragmentviews.data.mappers.toDomain
import org.sluman.mvvmfragmentviews.data.network.ApiClient
import org.sluman.mvvmfragmentviews.domain.CountryDomainEntity
import org.sluman.mvvmfragmentviews.domain.WalmartRepository

class WalmartRepositoryImpl(private val apiClient: ApiClient): WalmartRepository {
    override suspend fun getData(): Flow<Resource<List<CountryDomainEntity>>> {
        return flow {
            emit(Resource.Loading)
            try {
                val data = apiClient.apiService.getData().map { entity ->
                    entity.toDomain()
                }
                emit(Resource.Success(data))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error"))
            }

        }
    }
}