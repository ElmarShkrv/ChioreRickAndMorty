package com.example.chiorerickandmorty.ui.fragments.homefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.chiorerickandmorty.R
import com.example.chiorerickandmorty.adapter.homeadapters.HomeLoadStateAdapter
import com.example.chiorerickandmorty.adapter.homeadapters.HomeRvAdapter
import com.example.chiorerickandmorty.databinding.FragmentHomeBinding
import com.example.chiorerickandmorty.util.DefaultItemDecorator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeRvAdapter: HomeRvAdapter
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.allCharactersData.observe(viewLifecycleOwner) { allCharactersResponse ->
            homeRvAdapter.submitData(viewLifecycleOwner.lifecycle, allCharactersResponse)
        }

        setUpHomeRv()
        initAdapter()

    }

    private fun initAdapter() {

        binding.homeRv.adapter = homeRvAdapter.withLoadStateHeaderAndFooter(
            header = HomeLoadStateAdapter { homeRvAdapter.retry() },
            footer = HomeLoadStateAdapter { homeRvAdapter.retry() },
        )

//        feedAdapter.addLoadStateListener { loadState ->
//            binding.feedRv.isVisible = loadState.source.refresh is LoadState.NotLoading
//            binding.shimmerLayout.isVisible = loadState.source.refresh is LoadState.Loading
//            binding.imgListType.isInvisible = loadState.source.refresh is LoadState.Loading
//            binding.txtListType.isInvisible = loadState.source.refresh is LoadState.Loading
//            //binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
//            binding.retryBtn.isVisible = loadState.source.refresh is LoadState.Error
//            handleError(loadState)
//        }

        binding.retryBtn.setOnClickListener {
            homeRvAdapter.retry()
        }
    }

    private fun setUpHomeRv() {
        homeRvAdapter = HomeRvAdapter()
        binding.apply {
            homeRv.adapter = homeRvAdapter

            homeRvAdapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

            homeRv.addItemDecoration(DefaultItemDecorator(
                resources.getDimensionPixelSize(R.dimen.horizontal_margin),
                resources.getDimensionPixelSize(R.dimen.vertical_margin)
            ))
        }
    }

}