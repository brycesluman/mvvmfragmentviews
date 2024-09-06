package org.sluman.mvvmfragmentviews.data.network

import org.sluman.mvvmfragmentviews.data.CountryNetworkEntity
import retrofit2.http.GET

interface ApiService {
    @GET("countries.json")
    suspend fun getData(): List<CountryNetworkEntity>

}