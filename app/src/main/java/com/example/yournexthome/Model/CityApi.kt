package com.example.yournexthome.Model

import CitiesResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CityApi {
    @GET("datastore_search")
    fun getCities(
        @Query("resource_id") resourceId: String,
        @Query("limit") limit: Int
    ): Call<CitiesResult>
}
