package com.isspass.myapplication.ui.view.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.viewModels
import com.google.android.gms.location.*
import com.isspass.myapplication.R
import com.isspass.myapplication.databinding.ActivityMainBinding
import com.isspass.myapplication.ui.adapter.IssLocationsAdapter
import com.isspass.myapplication.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null
    private val LOCATION_REQUEST_INTERVAL: Long = 5000

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setSupportActionBar(viewBinding.toolbar)

        supportFragmentManager.beginTransaction()
            .replace(R.id.content, MainFragment()).commit()

        getCurrentLocation()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun getCurrentLocation() {

        if (isLocationEnabled()) {

            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

            locationCallback = object : LocationCallback() {

                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    viewModel.onLocationObtained(locationResult.lastLocation)

                    updateAddressText(locationResult)

                    mFusedLocationProviderClient?.removeLocationUpdates(this)
                }
            }

            createLocationRequest()

        }else{
            Toast.makeText(this, resources.getString(R.string.location_not_enabled), Toast.LENGTH_LONG).show()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }

    private fun updateAddressText(locationResult: LocationResult) {
        val addresses: List<Address>
        val geocoder = Geocoder(this, Locale.getDefault())

        addresses = geocoder.getFromLocation(
            locationResult.lastLocation.latitude,
            locationResult.lastLocation.longitude,
            1
        )

        val address: String =
            addresses[0].getAddressLine(0)

        viewBinding.tvCurrentLocation.text = getString(R.string.current_location, address)

    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.create()
        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest!!.setInterval(LOCATION_REQUEST_INTERVAL).fastestInterval =
            LOCATION_REQUEST_INTERVAL
        requestLocationUpdate()
    }

    private fun requestLocationUpdate() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED ) {

            Log.e("location permission", "denied")

            val requestPermissionLauncher =
                registerForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { isGranted: Boolean ->
                    if (isGranted) {
                        startRequestLocationUpdates()
                    } else {
                        Toast.makeText(this, resources.getString(R.string.permissions_not_granted), Toast.LENGTH_LONG).show()
                    }
                }

            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        }else{
            startRequestLocationUpdates()
        }

    }

    @SuppressLint("MissingPermission")
    private fun startRequestLocationUpdates() {
        mFusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest!!,
            locationCallback!!,
            Looper.myLooper()!!
        )
    }
}