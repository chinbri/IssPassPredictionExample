package com.isspass.myapplication.ui.view.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.gms.location.*
import com.isspass.domain.model.iss.IssLocationItemEntity
import com.isspass.myapplication.R
import com.isspass.myapplication.databinding.FragmentMainBinding
import com.isspass.myapplication.ui.UiStatus
import com.isspass.myapplication.ui.adapter.IssLocationsAdapter
import com.isspass.myapplication.ui.view.detail.LocationDetailFragment
import com.isspass.myapplication.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainFragment: Fragment() {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var viewBinding: FragmentMainBinding

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null
    private val LOCATION_REQUEST_INTERVAL: Long = 5000

    private lateinit var locationsAdapter: IssLocationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentMainBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
    }

    private fun setupViewModel() {

        viewModel.issPredictedData.observe(requireActivity()) { issPredictedData ->
            locationsAdapter.updateData(issPredictedData.locations)
        }

        viewModel.uiStatus.observe(requireActivity()) { uiStatus ->
            when (uiStatus) {

                is UiStatus.Loading -> {
                    viewBinding.cpiLoading.visibility = View.VISIBLE
                }

                is UiStatus.Error -> {
                    viewBinding.cpiLoading.visibility = View.GONE
                    Toast.makeText(requireContext(), uiStatus.message, Toast.LENGTH_LONG).show()
                }

                is UiStatus.Success -> viewBinding.cpiLoading.visibility = View.GONE

            }
        }

    }

    private fun setupView() {

        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        locationsAdapter = IssLocationsAdapter { issLocationItemEntity ->

            navigateToDetail(issLocationItemEntity)

        }

        viewBinding.rvLocations.apply {
            adapter = locationsAdapter
            addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
        }

        getCurrentLocation()

    }

    private fun navigateToDetail(issLocationItemEntity: IssLocationItemEntity) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, LocationDetailFragment.getInstance(issLocationItemEntity))
            .addToBackStack(LocationDetailFragment.TAG)
            .commit()
    }

    private fun getCurrentLocation() {

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        locationCallback = object : LocationCallback() {

            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                viewModel.onLocationObtained(locationResult.lastLocation)

                updateAddressText(locationResult)

                mFusedLocationProviderClient?.removeLocationUpdates(this)
            }
        }

        createLocationRequest()
    }

    private fun updateAddressText(locationResult: LocationResult) {
        val addresses: List<Address>
        val geocoder = Geocoder(requireContext(), Locale.getDefault())

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

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED ) {

            Log.e("location permission", "denied")

            val requestPermissionLauncher =
                registerForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { isGranted: Boolean ->
                    if (isGranted) {
                        startRequestLocationUpdates()
                    } else {
                        Toast.makeText(requireContext(), resources.getString(R.string.permissions_not_granted), Toast.LENGTH_LONG).show()
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