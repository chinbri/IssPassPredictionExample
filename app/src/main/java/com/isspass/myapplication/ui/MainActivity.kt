package com.isspass.myapplication.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
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
import java.util.*


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

        supportFragmentManager.beginTransaction()
            .replace(R.id.content, MainFragment()).commit()

    }

}