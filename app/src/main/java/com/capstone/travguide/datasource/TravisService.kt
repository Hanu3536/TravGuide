package com.capstone.travguide.datasource

import com.capstone.travguide.models.Location
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TravisService {

    @GET("/locations_near_me")
    suspend fun getAllLocations(@Query("latitude") latitude: String, @Query("longitude") longitude: String): Response<List<Location>>
}