package dao

import com.example.yournexthome.Model.City
import retrofit2.http.GET
import retrofit2.http.Query

interface CityService {
    @GET("endpoint") // Replace "endpoint" with the actual endpoint of your API
    suspend fun getCities(
        @Query("resource_id") resourceId: String,
        @Query("limit") limit: Int
    ): City
}
