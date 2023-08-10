package com.example.chiorerickandmorty.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chiorerickandmorty.data.model.location.Result
import com.example.chiorerickandmorty.databinding.LocationRowBinding
import com.example.chiorerickandmorty.ui.fragments.locationfragment.LocationFragmentDirections

class LocationRvAdapter() :
    PagingDataAdapter<Result, LocationRvAdapter.LocationViewHolder>(DiffUtilCallBack()) {

    class LocationViewHolder(val binding: LocationRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(location: Result) {
            with(binding) {

                itemView.setOnClickListener { view ->
                    val action = LocationFragmentDirections
                        .actionLocationFragmentToLocationCharactersBottomSheet(location.id)
                    Navigation.findNavController(view).navigate(action)
                }

                locationNameTv.text = location.name
                typeTv.text = location.type
                dimensionTv.text = location.dimension

            }

        }
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val result = getItem(position)
        if (result != null) {
            holder.bind(result)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(
            LocationRowBinding.inflate(
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