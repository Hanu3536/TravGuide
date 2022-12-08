package com.capstone.travguide.datasource

import com.capstone.travguide.TravisConstants.TRAVIS_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TravisDataSource {

    fun travisRetrofitInstance(): TravisService {
        return Retrofit.Builder()
            .baseUrl(TRAVIS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TravisService::class.java)
    }
}