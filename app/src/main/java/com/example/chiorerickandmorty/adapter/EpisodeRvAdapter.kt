package com.example.chiorerickandmorty.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chiorerickandmorty.data.model.Result
import com.example.chiorerickandmorty.databinding.EpisodeRowBinding

class EpisodeRvAdapter() :
    PagingDataAdapter<Result, EpisodeRvAdapter.EpisodeViewHolder>(DiffUtilCallBack()) {

    class EpisodeViewHolder(val binding: EpisodeRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(episode: Result) {
            with(binding) {


                episodeName.text = episode.name
                airDate.text = "Air Date - ${episode.air_date}"
                episodeId.text = episode.episode

            }

        }
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val result = getItem(position)
        if (result != null) {
            holder.bind(result)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder(
            EpisodeRowBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }

}