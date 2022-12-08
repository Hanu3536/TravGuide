package com.capstone.travguide.datasource

sealed class TravisNetworkResult<T>(var data: T? = null, var errorMessage: String = "") {

    class Loading<T>: TravisNetworkResult<T>()

    class Success<T>(data: T): TravisNetworkResult<T>(data = data)

    class Error<T>(errorMessage: String, data: T? = null): TravisNetworkResult<T>(data, errorMessage)
}
