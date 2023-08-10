package com.example.chiorerickandmorty.ui.fragments.locationcharactersbottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.chiorerickandmorty.adapter.BottomSheetCharactersRvAdapter
import com.example.chiorerickandmorty.databinding.BottomsheetLocationCharactersBinding
import com.example.chiorerickandmorty.util.Resource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationCharactersBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetLocationCharactersBinding
    private val args by navArgs<LocationCharactersBottomSheetArgs>()
    private val viewModel by viewModels<LocationCharactersViewModel>()
    private lateinit var bottomSheetCharactersRvAdapter: BottomSheetCharactersRvAdapter

    private val TAG = "LocationCharactersBottomSheet"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetLocationCharactersBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCharachetrByLocation(args.locationId)

        observeCharacterResponse()

    }

    private fun observeCharacterResponse() {
        lifecycleScope.launch {
            viewModel.characterByLocations.observe(viewLifecycleOwner) { characterLocationList ->
                when (characterLocationList) {
                    is Resource.Loading -> {
                        characterLocationList.message?.let { message ->
                            binding.bottomSheetLocationProgressBar.visibility = View.VISIBLE
                            Log.e(TAG, "An error occured: $message")
                        }
                    }

                    is Resource.Success -> {
                        characterLocationList.data?.let { detailsResponse ->
                            bottomSheetCharactersRvAdapter =
                                BottomSheetCharactersRvAdapter(detailsResponse.residents)
                            binding.apply {
                                bottomSheetLocationProgressBar.visibility = View.INVISIBLE
                                bottomLocationRv.adapter = bottomSheetCharactersRvAdapter
                                locationNameTv.text = detailsResponse.name
                                locationTypeTv.text = detailsResponse.type
                                locationDimensionTv.text = detailsResponse.dimension
                            }
                        }
                    }

                    is Resource.Error -> {
                        characterLocationList.message?.let { message ->
                            binding.bottomSheetLocationProgressBar.visibility = View.INVISIBLE
                            Log.e(TAG, "An error occured: $message")
                        }
                    }
                }

            }

        }
    }
}