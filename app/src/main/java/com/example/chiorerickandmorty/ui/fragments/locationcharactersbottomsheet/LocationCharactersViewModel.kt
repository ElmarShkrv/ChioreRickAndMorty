package com.example.chiorerickandmorty.ui.fragments.locationcharactersbottomsheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chiorerickandmorty.domain.models.Episode
import com.example.chiorerickandmorty.domain.models.Location
import com.example.chiorerickandmorty.repository.EpisodeCharactersRepository
import com.example.chiorerickandmorty.repository.LocationCharactersRepository
import com.example.chiorerickandmorty.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationCharactersViewModel @Inject constructor(
    private val repository: LocationCharactersRepository,
) : ViewModel() {

    private val _characterByLocations = MutableLiveData<Resource<Location?>>(Resource.Loading())
    val characterByLocations: LiveData<Resource<Location?>> = _characterByLocations

    init {
        _characterByLocations.value = Resource.Loading()
    }

    fun getCharachetrByLocation(locationId: Int) {

        viewModelScope.launch {
            val response = repository.getLocationByIdForCharachters(locationId)

            _characterByLocations.value = Resource.Success(response)
        }
    }

}