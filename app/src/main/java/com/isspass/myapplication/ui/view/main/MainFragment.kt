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
import androidx.fragment.app.activityViewModels
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

    private val viewModel by activityViewModels<MainViewModel>()

    private lateinit var viewBinding: FragmentMainBinding

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

    }

    private fun navigateToDetail(issLocationItemEntity: IssLocationItemEntity) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, LocationDetailFragment.getInstance(issLocationItemEntity))
            .addToBackStack(LocationDetailFragment.TAG)
            .commit()
    }


}