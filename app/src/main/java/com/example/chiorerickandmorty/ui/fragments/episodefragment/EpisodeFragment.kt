package com.example.chiorerickandmorty.ui.fragments.episodefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.example.chiorerickandmorty.R
import com.example.chiorerickandmorty.adapter.EpisodeRvAdapter
import com.example.chiorerickandmorty.databinding.FragmentEpisodeBinding
import com.example.chiorerickandmorty.util.DefaultItemDecorator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeFragment : Fragment() {

    private lateinit var binding: FragmentEpisodeBinding
    private lateinit var episodeRvAdapter: EpisodeRvAdapter
    private val viewModel by viewModels<EpisodeViewModel>()
    private val TAG = "EpisodeFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEpisodeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpEpiosdeRv()
        observeEpisodeData()
        initAdapter()

    }

    private fun initAdapter() {
        episodeRvAdapter.addLoadStateListener { loadState ->
            binding.apply {
                episodeRv.isVisible = loadState.source.refresh is LoadState.NotLoading
                episodeProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
            }

        }
    }

    private fun observeEpisodeData() {
        viewModel.allEpisodes.observe(viewLifecycleOwner) { allEpisodes ->
            episodeRvAdapter.submitData(viewLifecycleOwner.lifecycle, allEpisodes)

        }

    }

    private fun setUpEpiosdeRv() {
        binding.apply {
            episodeRvAdapter = EpisodeRvAdapter()
            episodeRv.adapter = episodeRvAdapter

            episodeRv.addItemDecoration(
                DefaultItemDecorator(
                    resources.getDimensionPixelSize(R.dimen.horizontal_margin_for_vertical),
                    resources.getDimensionPixelSize(R.dimen.vertical_margin_for_horizontal)
                )
            )

        }
    }

}