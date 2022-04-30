package com.isspass.myapplication.ui.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.isspass.domain.model.iss.IssLocationItemEntity
import com.isspass.myapplication.databinding.FragmentDetailBinding

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