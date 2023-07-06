package com.example.chiorerickandmorty.adapter.detailsadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chiorerickandmorty.data.model.Result
import com.example.chiorerickandmorty.databinding.EpisodeRowBinding
import com.example.chiorerickandmorty.domain.models.Episode

class DetailsRvAdapter(var episodeList: List<Episode>) :
    RecyclerView.Adapter<DetailsRvAdapter.DetailViewHolder>() {

    class DetailViewHolder(val binding: EpisodeRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val binding = EpisodeRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val currentItem = episodeList[position]

        holder.binding.apply {

//            holder.itemView.setOnClickListener { view ->
//                val action = DetailsFragmentDirections
//                    .actionDetailsFragmentToBottomSheetFragment(currentItem.id)
//                Navigation.findNavController(view).navigate(action)
//            }

            episodeName.text = currentItem.name
            airDate.text = "Air Date - ${currentItem.airDate}"
            episodeId.text = currentItem.episode
        }
    }

    override fun getItemCount(): Int {
        return episodeList.size
    }

}
