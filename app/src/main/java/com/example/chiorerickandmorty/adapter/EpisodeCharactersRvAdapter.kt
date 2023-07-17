package com.example.chiorerickandmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.chiorerickandmorty.R
import com.example.chiorerickandmorty.databinding.BottomRowBinding
import com.example.chiorerickandmorty.domain.models.Character

class EpisodeCharactersRvAdapter(var characterList: List<Character>) :
    RecyclerView.Adapter<EpisodeCharactersRvAdapter.DetailViewHolder>() {

    class DetailViewHolder(val binding: BottomRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val binding = BottomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val currentItem = characterList[position]

        holder.binding.apply {

            Glide.with(root)
                .load(currentItem.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_launcher_background)
                .into(characterImageView)

            characterNameTextView.text = currentItem.name
        }
    }

    override fun getItemCount(): Int = characterList.size

}
