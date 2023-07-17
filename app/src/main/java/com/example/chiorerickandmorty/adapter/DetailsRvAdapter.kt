package com.example.chiorerickandmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.chiorerickandmorty.databinding.DetailsEpisodeRowBinding
import com.example.chiorerickandmorty.domain.models.Episode
import com.example.chiorerickandmorty.ui.fragments.detailsfragment.DetailsFragmentDirections

class DetailsRvAdapter(var episodeList: List<Episode>) :
    RecyclerView.Adapter<DetailsRvAdapter.DetailViewHolder>() {

    class DetailViewHolder(val binding: DetailsEpisodeRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val binding = DetailsEpisodeRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val currentItem = episodeList[position]

        holder.binding.apply {

            holder.itemView.setOnClickListener { view ->
                val action = DetailsFragmentDirections
                    .actionDetailsFragmentToEpisodeCharactersBottomSheet(currentItem.id)
                Navigation.findNavController(view).navigate(action)
            }

            episodeName.text = currentItem.name
            airDate.text = "Air Date - ${currentItem.airDate}"
            episodeId.text = currentItem.episode
        }
    }

    override fun getItemCount(): Int {
        return episodeList.size
    }

}
