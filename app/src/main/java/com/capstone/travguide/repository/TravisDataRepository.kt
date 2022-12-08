package com.capstone.travguide.repository

import com.capstone.travguide.datasource.TravisDataSource
import com.capstone.travguide.datasource.TravisNetworkResult
import com.capstone.travguide.datasource.TravisService
import com.capstone.travguide.models.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow

class TravisDataRepository {

    private val travisService: TravisService = TravisDataSource.travisRetrofitInstance()

    suspend fun getAllLocations(latitude: String, longitude: String): Flow<TravisNetworkResult<List<Location>>> {
        try {
            val locationsResult = travisService.getAllLocations(latitude, longitude)
            if (locationsResult.body() != null) {
                return flow<TravisNetworkResult<List<Location>>> {
                    emit(TravisNetworkResult.Success(data = locationsResult.body() as List<Location>))
                }.distinctUntilChanged()
            }
        } catch (e: Exception) {
            return flow<TravisNetworkResult<List<Location>>> {
                emit(TravisNetworkResult.Error(errorMessage = "Error is ${e.localizedMessage}", data = null))
            }.distinctUntilChanged()
        }

        return flow {}
    }
}