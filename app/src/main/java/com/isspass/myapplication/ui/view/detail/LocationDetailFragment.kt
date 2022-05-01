package com.isspass.myapplication.ui.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.isspass.domain.model.iss.IssLocationItemEntity
import com.isspass.domain.model.iss.durationMinutes
import com.isspass.domain.model.iss.durationSecondsWithoutMinutes
import com.isspass.myapplication.R
import com.isspass.myapplication.databinding.FragmentDetailBinding
import com.isspass.myapplication.ui.UiStatus
import com.isspass.myapplication.viewmodel.detail.DetailViewModel
import com.isspass.myapplication.viewmodel.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LocationDetailFragment: Fragment() {

    private val viewModel by viewModels<DetailViewModel>()

    private lateinit var viewBinding: FragmentDetailBinding
    private lateinit var locationItemEntity: IssLocationItemEntity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentDetailBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
        viewModel.initialize(locationItemEntity)
    }

    private fun setupView() {
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewBinding.tvRemainingTime.text = buildRemainingSecondsString(locationItemEntity.riseTime)
        viewBinding.tvDuration.text = resources.getString(
            R.string.detail_duration_label,
            locationItemEntity.durationMinutes,
            locationItemEntity.durationSecondsWithoutMinutes
        )

    }

    private fun setupViewModel() {

        viewModel.factForNumber.observe(requireActivity()){ factForNumber ->
            viewBinding.tvFact.text = resources.getString(R.string.detail_fact_text, locationItemEntity.duration, factForNumber)
        }

        viewModel.uiStatus.observe(requireActivity()) { uiStatus ->
            when (uiStatus) {

                is UiStatus.Loading -> {
                    viewBinding.tvFact.text = resources.getString(R.string.loading)
                }

                is UiStatus.Error -> {
                    viewBinding.tvFact.text = resources.getString(R.string.error_obtaining_number_fact, uiStatus.message)
                }

                is UiStatus.Success -> {}

            }
        }

    }

    private fun buildRemainingSecondsString(riseTime: Long): String {
        val diffInSeconds: Long = riseTime - Date().time / 1000
        return diffInSeconds.toString()
    }

    companion object{

        val TAG: String = LocationDetailFragment::class.java.name

        fun getInstance(locationItemEntity: IssLocationItemEntity): LocationDetailFragment{
            val fragment = LocationDetailFragment()
            fragment.locationItemEntity = locationItemEntity
            return fragment
        }

    }
}