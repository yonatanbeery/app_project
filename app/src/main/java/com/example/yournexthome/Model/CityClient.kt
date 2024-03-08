package com.example.yournexthome.Model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CityClient {
    private const val BASE_URL = "https://data.gov.il/api/3/action/"
    private val retrofit: Retrofit

    init {
        try {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("Error initializing Retrofit: ${e.message}")
        }
    }

    val cityApi: CityApi by lazy {
        retrofit.create(CityApi::class.java)
    }
}
