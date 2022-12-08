package com.capstone.travguide.fragments

import android.Manifest
import android.content.Context
import android.location.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.capstone.travguide.adapters.TravisLocationsRecyclerAdapter
import com.capstone.travguide.databinding.FragmentTravisLocationsListBinding
import com.capstone.travguide.datasource.TravisNetworkResult
import com.capstone.travguide.viewmodels.TravisViewModel
import kotlinx.coroutines.flow.collectLatest
import java.util.*


class TravisLocationsListFragment : Fragment() {

    private lateinit var binding: FragmentTravisLocationsListBinding
    private lateinit var locationsRecyclerAdapter: TravisLocationsRecyclerAdapter

    private val travisViewModel by activityViewModels<TravisViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTravisLocationsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationPermissionRequesting()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            travisViewModel.locationsFlow.collectLatest { locationsResult ->
                when (locationsResult) {
                    is TravisNetworkResult.Loading -> {}
                    is TravisNetworkResult.Success -> {
                        locationsRecyclerAdapter = TravisLocationsRecyclerAdapter(requireContext(), locationsResult.data!!)
                        binding.rvLocationsList.adapter = locationsRecyclerAdapter
                    }
                    is TravisNetworkResult.Error -> {
                        Toast.makeText(context, "Error is ${locationsResult.errorMessage}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun locationPermissionRequesting() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                    Toast.makeText(
                        context,
                        "Precise Location Permission Granted!",
                        Toast.LENGTH_SHORT
                    ).show()
                    getCurrentLocation()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                    Toast.makeText(
                        context,
                        "Approximate Location Permission Granted!",
                        Toast.LENGTH_SHORT
                    ).show()
                    getCurrentLocation()
                }
                else -> {
                    // No location access granted.
                    Toast.makeText(context, "No Location Access Granted!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun getCurrentLocation() {
        val locationManager: LocationManager = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var locationByGps: Location? = null

        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        val gpsLocationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                locationByGps = location
            }

            @Deprecated("Deprecated in Java")
            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        if (hasGps) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                0F,
                gpsLocationListener
            )
        }

        val lastKnownLocationByGps =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        lastKnownLocationByGps?.let {
            locationByGps = lastKnownLocationByGps
        }

        if (locationByGps != null) {
            Log.e("Location lat lon", "Latitude : ${locationByGps!!.latitude} and Longitude : ${locationByGps!!.longitude}")

            binding.tvLocationName.text = getRegionName(locationByGps!!.latitude, locationByGps!!.longitude)
        }

        travisViewModel.getAllLocations()
    }

    private fun getRegionName(latitude: Double, longitude: Double): String {
        var regionName = ""
        val geoCoder = Geocoder(requireContext(), Locale.getDefault())

        try {
            val addresses: List<Address> = geoCoder.getFromLocation(latitude, longitude, 1)
            if (addresses.isNotEmpty()) {
                regionName = addresses[0].locality
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return regionName
    }
}