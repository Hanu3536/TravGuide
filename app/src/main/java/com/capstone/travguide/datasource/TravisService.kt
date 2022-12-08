package com.capstone.travguide.datasource

import com.capstone.travguide.models.Location
import retrofit2.Response
import retrofit2.http.GET

interface TravisService {

    @GET("/locationseq")
    suspend fun getAllLocations(): Response<List<Location>>
}