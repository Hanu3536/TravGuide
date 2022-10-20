package com.capstone.travguide

import android.Manifest
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.getSystemService


class TravisHomePageFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_travis_home_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                    Toast.makeText(context, "Precise Location Permission Granted!", Toast.LENGTH_SHORT).show()
                    getCurrentLocation()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                    Toast.makeText(context, "Approximate Location Permission Granted!", Toast.LENGTH_SHORT).show()
                } else -> {
                // No location access granted.
                Toast.makeText(context, "No Location Access Granted!", Toast.LENGTH_SHORT).show()
            }
            }
        }

        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    private fun getCurrentLocation() {
        var currentLocation: Location? = null
        val locationManager: LocationManager = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        var locationByNetwork: Location? = null
        var locationByGps: Location? = null

        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        val gpsLocationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                locationByGps = location
            }

            @Deprecated("Deprecated in Java")
            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

//        val networkLocationListener: LocationListener = object : LocationListener {
//            override fun onLocationChanged(location: Location) {
//                locationByNetwork= location
//            }
//
//            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
//            override fun onProviderEnabled(provider: String) {}
//            override fun onProviderDisabled(provider: String) {}
//        }

        if (hasGps) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                0F,
                gpsLocationListener
            )
        }

//        if (hasNetwork) {
//            locationManager.requestLocationUpdates(
//                LocationManager.NETWORK_PROVIDER,
//                5000,
//                0F,
//                networkLocationListener
//            )
//        }

        val lastKnownLocationByGps =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        lastKnownLocationByGps?.let {
            locationByGps = lastKnownLocationByGps
        }

//        val lastKnownLocationByNetwork =
//            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
//        lastKnownLocationByNetwork?.let {
//            locationByNetwork = lastKnownLocationByNetwork
//        }

        if (locationByGps != null) {
            Toast.makeText(context, "Latitude : ${locationByGps!!.latitude} and Longitude : ${locationByGps!!.longitude}", Toast.LENGTH_LONG).show()

            Log.d("LOCATIONLATLON", "Latitude : ${locationByGps!!.latitude} and Longitude : ${locationByGps!!.longitude}")
        }
//        if (locationByGps != null && locationByNetwork != null) {
//            if (locationByGps.accuracy > locationByNetwork!!.accuracy) {
//                currentLocation = locationByGps
//                latitude = currentLocation.latitude
//                longitude = currentLocation.longitude
//                // use latitude and longitude as per your need
//            } else {
//                currentLocation = locationByNetwork
//                latitude = currentLocation.latitude
//                longitude = currentLocation.longitude
//                // use latitude and longitude as per your need
//            }
//        }
    }
}