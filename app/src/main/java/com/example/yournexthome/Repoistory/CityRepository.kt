package com.example.yournexthome.Repository

import CitiesResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yournexthome.Model.City
import com.example.yournexthome.Model.CityClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityRepository {
    fun getCities(resourceId: String, limit: Int, callback: (List<City>) -> Unit): LiveData<List<City>> {
        val citiesLiveData = MutableLiveData<List<City>>()

        try {
            CityClient.cityApi.getCities(resourceId, limit).enqueue(object : Callback<CitiesResult> {
                override fun onResponse(call: Call<CitiesResult>, response: Response<CitiesResult>) {
                    if (response.isSuccessful) {
                        val citiesResult = response.body()
                        citiesResult?.let {
                            callback(it.result.records)
                        }
                    } else {
                        println("error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<CitiesResult>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return citiesLiveData
    }
}
