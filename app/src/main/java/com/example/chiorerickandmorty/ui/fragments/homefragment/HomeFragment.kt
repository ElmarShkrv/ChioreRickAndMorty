package com.example.chiorerickandmorty.ui.fragments.homefragment

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.example.chiorerickandmorty.R
import com.example.chiorerickandmorty.adapter.homeadapters.HomeLoadStateAdapter
import com.example.chiorerickandmorty.adapter.homeadapters.HomeRvAdapter
import com.example.chiorerickandmorty.data.model.Characters
import com.example.chiorerickandmorty.databinding.FragmentFilterBinding
import com.example.chiorerickandmorty.databinding.FragmentHomeBinding
import com.example.chiorerickandmorty.extensions.getTextButtonChecked
import com.example.chiorerickandmorty.extensions.getTextChipChecked
import com.example.chiorerickandmorty.extensions.setButtonChecked
import com.example.chiorerickandmorty.extensions.setChipChecked
import com.example.chiorerickandmorty.util.DataFilter
import com.example.chiorerickandmorty.util.DefaultItemDecorator
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeRvAdapter: HomeRvAdapter
    private val viewModel by viewModels<HomeViewModel>()
    private val TAG = "Home"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)

        setToolbar(binding, inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpHomeRv()
        initAdapter()
        observeFilteredData()

        viewModel.setFilter(DataFilter.All)

    }

    private fun setToolbar(binding: FragmentHomeBinding, inflater: LayoutInflater) {
        binding.filterIv.setOnClickListener {
            showFilterDialog(inflater)
        }
    }

    private fun showFilterDialog(inflater: LayoutInflater) {
        val bindingForFilter = FragmentFilterBinding.inflate(inflater)
        val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(bindingForFilter.root)
        initFilter(bindingForFilter)
        dialog.show()
    }

    private fun initFilter(binding: FragmentFilterBinding) {
        binding.apply {

            binding.btnMakeFilter.setOnClickListener {

                if (chipgroupStatus.getTextChipChecked()
                        .isNotEmpty() && radiogroupGender.getTextButtonChecked().isNotEmpty()
                ) {
                    viewModel.setFilter(
                        DataFilter.StatusAndGender(
                            chipgroupStatus.getTextChipChecked(),
                            radiogroupGender.getTextButtonChecked()
                        )
                    )
                } else {
                    if (chipgroupStatus.getTextChipChecked().isNotEmpty()) {
                        viewModel.setFilter(DataFilter.Status(chipgroupStatus.getTextChipChecked()))
                    } else {
                        viewModel.setFilter(DataFilter.Gender(radiogroupGender.getTextButtonChecked()))
                    }
                }

            }
        }
    }

    private fun observeFilteredData() {
        viewModel.test.observe(viewLifecycleOwner) { filteredData ->
            if (filteredData == null) {
                Log.e(TAG, "filterdata equal null")
            }
            filteredData?.let {
                homeRvAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun initAdapter() {

        binding.homeRv.adapter = homeRvAdapter.withLoadStateHeaderAndFooter(
            header = HomeLoadStateAdapter { homeRvAdapter.retry() },
            footer = HomeLoadStateAdapter { homeRvAdapter.retry() },
        )

        homeRvAdapter.addLoadStateListener { loadState ->
            binding.homeRv.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.shimmerLayout.isVisible = loadState.source.refresh is LoadState.Loading
            binding.tvHomeSearch.isInvisible = loadState.source.refresh is LoadState.Loading
            binding.filterIv.isInvisible = loadState.source.refresh is LoadState.Loading
            binding.retryBtn.isVisible = loadState.source.refresh is LoadState.Error
            handleError(loadState)
        }

        binding.retryBtn.setOnClickListener {
            homeRvAdapter.retry()
        }
    }

    private fun handleError(loadState: CombinedLoadStates) {
        val errorStates = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error

        errorStates?.let {
            Toast.makeText(requireContext(), "${it.error}", Toast.LENGTH_LONG).show()
        }

    }

    private fun setUpHomeRv() {
        homeRvAdapter = HomeRvAdapter()
        binding.apply {
            homeRv.adapter = homeRvAdapter

            homeRvAdapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

            homeRv.addItemDecoration(
                DefaultItemDecorator(
                    resources.getDimensionPixelSize(R.dimen.horizontal_margin),
                    resources.getDimensionPixelSize(R.dimen.vertical_margin)
                )
            )
        }
    }

}