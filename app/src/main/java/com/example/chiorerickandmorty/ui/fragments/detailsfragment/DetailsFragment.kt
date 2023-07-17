package com.example.chiorerickandmorty.ui.fragments.detailsfragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.chiorerickandmorty.R
import com.example.chiorerickandmorty.adapter.DetailsRvAdapter
import com.example.chiorerickandmorty.data.model.Characters
import com.example.chiorerickandmorty.databinding.FragmentDetailsBinding
import com.example.chiorerickandmorty.enum.CharacterStatusEnums
import com.example.chiorerickandmorty.util.DefaultItemDecorator
import com.example.chiorerickandmorty.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()
    private val viewModel by viewModels<DetailsViewModel>()
    private lateinit var detailsRvAdapter: DetailsRvAdapter

    val TAG = "DetailsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.characterById(args.characterId)
        viewModel.getCharacterEpisodes(args.characterId)
//        viewModel.getCharachetrByEpisode(args)
        observeDetailsResponse()
        observeDetailsEpiosdeResponse()
        setupRv()

    }

    private fun observeDetailsEpiosdeResponse() {
        lifecycleScope.launch {
            viewModel.characterEpisodes.observe(viewLifecycleOwner) { characterEpisodeList ->
                when (characterEpisodeList) {
                    is Resource.Loading -> {
                        characterEpisodeList.message?.let { message ->
                            binding.episodeProgressBar.visibility = View.VISIBLE
                            Log.e(TAG, "An error occured: $message")
                        }
                    }

                    is Resource.Success -> {
                        binding.episodeProgressBar.visibility = View.INVISIBLE
                        characterEpisodeList.data?.let { detailsResponse ->
                            detailsRvAdapter =
                                DetailsRvAdapter(detailsResponse.episodeList)
                            binding.episodeRv.adapter = detailsRvAdapter
                        }
                    }

                    is Resource.Error -> {
                        characterEpisodeList.message?.let { message ->
                            binding.episodeProgressBar.visibility = View.INVISIBLE
                            Log.e(TAG, "An error occured: $message")
                        }
                    }
                }

            }

        }
    }

    private fun setupRv() {
        binding.apply {
            episodeRv.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
            )

            episodeRv.addItemDecoration(
                DefaultItemDecorator(
                    resources.getDimensionPixelSize(R.dimen.horizontal_margin_for_horizontal),
                    resources.getDimensionPixelSize(R.dimen.vertical_margin_for_vertical)
                )
            )
        }
    }

    private fun observeDetailsResponse() {
        lifecycleScope.launch {
            viewModel.detailsCharacter.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Loading -> {
                        response.message?.let { message ->
                            Log.e(TAG, "An error occured: $message")
                        }
                    }

                    is Resource.Success -> {
                        response.data?.let { detailsResponse ->
                            setupUi(detailsResponse)
                        }
                    }

                    is Resource.Error -> {
                        response.message?.let { message ->
                            Log.e(TAG, "An error occured: $message")
                        }
                    }
                }
            }
        }
    }

    private fun setupUi(detailsResponse: Characters) {
        binding.apply {

            Glide.with(root).load(detailsResponse.image).into(characterImage)

            characterName.text = detailsResponse.name
            characterStatusAndGenderTv.text =
                "${detailsResponse.status + " - " + detailsResponse.gender}"

            when (detailsResponse.status) {
                CharacterStatusEnums.CHARACTER_ALIVE.value -> charachterStatus.setBackgroundResource(
                    R.color.character_alive
                )

                CharacterStatusEnums.CHARACTER_DEAD.value -> charachterStatus.setBackgroundResource(
                    R.color.character_dead
                )

                CharacterStatusEnums.CHARACTER_UNKNOWN.value -> charachterStatus.setBackgroundResource(
                    R.color.character_unknown
                )

                else -> {
                    charachterStatus.setBackgroundResource(R.color.character_else)
                }

            }

            shareCharachterButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, detailsResponse.url)

                val chooser = Intent.createChooser(intent, "Share using...")
                startActivity(chooser)
            }

        }
    }

}
