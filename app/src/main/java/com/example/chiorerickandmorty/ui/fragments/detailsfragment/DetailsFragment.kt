package com.example.chiorerickandmorty.ui.fragments.detailsfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.chiorerickandmorty.R
import com.example.chiorerickandmorty.data.model.Characters
import com.example.chiorerickandmorty.databinding.FragmentDetailsBinding
import com.example.chiorerickandmorty.enum.CharacterStatusEnums
import com.example.chiorerickandmorty.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()
    private val viewModel by viewModels<DetailsViewModel>()

    val TAG = "DetailsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.characterById(args.characterId)
        observeDetailsResponse()

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
            characterStatusAndGenderTv.text = "${detailsResponse.status + " - " + detailsResponse.gender}"

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

        }
    }

}
