package com.capstone.travguide.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.travguide.datasource.TravisNetworkResult
import com.capstone.travguide.models.Location
import com.capstone.travguide.repository.TravisDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TravisViewModel : ViewModel() {

    private val travisDataRepository
        get() = TravisDataRepository()

    private val _locationsFlow: MutableStateFlow<TravisNetworkResult<List<Location>>> = MutableStateFlow(TravisNetworkResult.Loading())
    val locationsFlow: StateFlow<TravisNetworkResult<List<Location>>> get() = _locationsFlow

    fun getAllLocations() {
        viewModelScope.launch {
            travisDataRepository.getAllLocations().collectLatest {
                _locationsFlow.emit(it)
            }
        }
    }
}