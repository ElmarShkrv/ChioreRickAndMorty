package com.example.chiorerickandmorty.ui.fragments.episodecharctersbottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.chiorerickandmorty.adapter.BottomSheetCharactersRvAdapter
import com.example.chiorerickandmorty.databinding.BottomsheetEpisodeCharactersBinding
import com.example.chiorerickandmorty.util.Resource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EpisodeCharactersBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetEpisodeCharactersBinding
    private val args by navArgs<EpisodeCharactersBottomSheetArgs>()
    private val viewModel by viewModels<EpisodeCharactersViewModel>()
    private lateinit var bottomSheetCharactersRvAdapter: BottomSheetCharactersRvAdapter

    private val TAG = "EpisodeCharactersBottomSheet"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetEpisodeCharactersBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCharachetrByEpisode(args.episodeId)

        observeCharacterResponse()

    }

    private fun observeCharacterResponse() {
        lifecycleScope.launch {
            viewModel.characterByEpisodes.observe(viewLifecycleOwner) { characterEpisodeList ->
                when (characterEpisodeList) {
                    is Resource.Loading -> {
                        characterEpisodeList.message?.let { message ->
                            binding.bottomSheetProgressBar.visibility = View.VISIBLE
                            Log.e(TAG, "An error occured: $message")
                        }
                    }

                    is Resource.Success -> {
                        characterEpisodeList.data?.let { detailsResponse ->
                            bottomSheetCharactersRvAdapter =
                                BottomSheetCharactersRvAdapter(detailsResponse.characters)
                            binding.apply {
                                bottomSheetProgressBar.visibility = View.INVISIBLE
                                bottomRv.adapter = bottomSheetCharactersRvAdapter
                                episodeNumberTv.text = detailsResponse.episode
                                episodeAirDateTv.text = detailsResponse.airDate
                                episodeNameTv.text = detailsResponse.name
                            }
                        }
                    }

                    is Resource.Error -> {
                        characterEpisodeList.message?.let { message ->
                            binding.bottomSheetProgressBar.visibility = View.INVISIBLE
                            Log.e(TAG, "An error occured: $message")
                        }
                    }
                }

            }

        }
    }

}