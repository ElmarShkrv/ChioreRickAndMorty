package com.example.chiorerickandmorty.adapter.homeadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chiorerickandmorty.databinding.ItemLoadStateBinding

class HomeLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<HomeLoadStateAdapter.HomeLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): HomeLoadStateViewHolder {
        val binding =
            ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return HomeLoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class HomeLoadStateViewHolder(private val binding: ItemLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryBtn.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                retryBtn.isVisible = loadState !is LoadState.Loading
                errorTv.isVisible = loadState !is LoadState.Loading
            }
        }

    }


}