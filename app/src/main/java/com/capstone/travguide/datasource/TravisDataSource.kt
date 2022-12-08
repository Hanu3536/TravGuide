package com.capstone.travguide.datasource

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TravisDataSource {

    fun travisRetrofitInstance(): TravisService {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TravisService::class.java)
    }
}