package com.isspass.myapplication.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.gms.location.*
import com.isspass.myapplication.R
import com.isspass.myapplication.databinding.ActivityMainBinding
import com.isspass.myapplication.ui.adapter.IssLocationsAdapter
import com.isspass.myapplication.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var viewBinding: ActivityMainBinding

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null
    private val LOCATION_REQUEST_INTERVAL: Long = 5000

    private lateinit var locationsAdapter: IssLocationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setupView()

        viewModel.issPredictedData.observe(this){ issPredictedData ->
            locationsAdapter.updateData(issPredictedData.locations)
        }
        viewModel.testMessage.observe(this){ value ->

        }

    }

    private fun setupView() {

        locationsAdapter = IssLocationsAdapter()

        viewBinding.rvLocations.apply {
            adapter = locationsAdapter
            addItemDecoration(
                DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            )
        }

        getCurrentLocation()

    }

    private fun getCurrentLocation() {

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {

            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                viewModel.onLocationObtained(locationResult.lastLocation)

                mFusedLocationProviderClient?.removeLocationUpdates(this)
            }
        }

        createLocationRequest()
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