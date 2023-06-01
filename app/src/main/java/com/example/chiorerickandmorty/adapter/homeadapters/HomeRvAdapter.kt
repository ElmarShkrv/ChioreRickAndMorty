package com.example.chiorerickandmorty.adapter.homeadapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chiorerickandmorty.R
import com.example.chiorerickandmorty.data.model.Characters
import com.example.chiorerickandmorty.databinding.FragmentHomeRvBinding
import com.example.chiorerickandmorty.enum.CharacterStatusEnums

class HomeRvAdapter() :
    PagingDataAdapter<Characters, HomeRvAdapter.HomeViewHolder>(DiffUtilCallBack()) {

    class HomeViewHolder(val binding: FragmentHomeRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(characters: Characters) {
            with(binding) {
                Glide.with(root).load(characters.image).into(characterImage)

                characterSpeciesTv.text = "Species: ${characters.species}"
                characterNameTv.text = characters.name
                characterGenderTv.text = "Gender: ${characters.gender}"

                when (characters.status) {
                    CharacterStatusEnums.CHARACTER_ALIVE.value -> characterStatusIv.setImageResource(
                        R.color.character_alive
                    )

                    CharacterStatusEnums.CHARACTER_DEAD.value -> characterStatusIv.setImageResource(
                        R.color.character_dead
                    )

                    CharacterStatusEnums.CHARACTER_UNKNOWN.value -> characterStatusIv.setImageResource(
                        R.color.character_unknown
                    )

                    else -> {
                        characterStatusIv.setImageResource(R.color.character_else)
                    }

                }


            }

        }
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val result = getItem(position)
        if (result != null) {
            holder.bind(result)
        }

        holder.itemView.animation = AnimationUtils.loadAnimation(
            holder.itemView.context,
            R.anim.scale_up
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            FragmentHomeRvBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Characters>() {
        override fun areItemsTheSame(oldItem: Characters, newItem: Characters): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Characters, newItem: Characters): Boolean {
            return oldItem == newItem
        }

    }

}