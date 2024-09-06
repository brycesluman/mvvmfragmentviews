package org.sluman.mvvmfragmentviews.domain

import kotlinx.coroutines.flow.Flow
import org.sluman.mvvmfragmentviews.data.Resource

interface WalmartRepository {
    suspend fun getData(): Flow<Resource<List<CountryDomainEntity>>>
}