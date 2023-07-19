package com.example.chiorerickandmorty.ui.fragments.locationfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.chiorerickandmorty.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: LocationRepository,
) : ViewModel() {

    val allLocations = repository.getAllLocations().cachedIn(viewModelScope)

}