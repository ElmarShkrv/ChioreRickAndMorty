package com.example.chiorerickandmorty.ui.fragments.locationfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.example.chiorerickandmorty.R
import com.example.chiorerickandmorty.adapter.LocationRvAdapter
import com.example.chiorerickandmorty.databinding.FragmentLocationBinding
import com.example.chiorerickandmorty.util.DefaultItemDecorator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFragment: Fragment() {

    private lateinit var binding: FragmentLocationBinding
    private lateinit var locationRvAdapter: LocationRvAdapter
    private val viewModel by viewModels<LocationViewModel>()
    private val TAG = "LocationFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLocationRv()
        observeLocationData()
        setupLoading()

    }

    private fun setupLoading() {
        locationRvAdapter.addLoadStateListener { loadState ->
            binding.apply {
                locationRv.isVisible = loadState.source.refresh is LoadState.NotLoading
                locationProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
            }
        }
    }

    private fun observeLocationData() {
        viewModel.allLocations.observe(viewLifecycleOwner) {allLocations ->
            locationRvAdapter.submitData(viewLifecycleOwner.lifecycle, allLocations)
        }
    }

    private fun setupLocationRv() {
        binding.apply {
            locationRvAdapter = LocationRvAdapter()
            locationRv.adapter = locationRvAdapter

            locationRv.addItemDecoration(
                DefaultItemDecorator(
                    resources.getDimensionPixelSize(R.dimen.horizontal_margin_for_vertical),
                    resources.getDimensionPixelSize(R.dimen.vertical_margin_for_horizontal)
                )
            )
        }
    }

}