package com.isspass.myapplication.ui.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.isspass.domain.model.iss.IssLocationItemEntity
import com.isspass.domain.model.iss.durationMinutes
import com.isspass.domain.model.iss.durationSecondsWithoutMinutes
import com.isspass.myapplication.R
import com.isspass.myapplication.databinding.FragmentDetailBinding
import java.util.*

class LocationDetailFragment: Fragment() {

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
        viewBinding.tvFact.text = resources.getString(R.string.detail_fact_text, locationItemEntity.duration, locationItemEntity.fact)
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