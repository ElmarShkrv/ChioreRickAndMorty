package com.example.chiorerickandmorty.ui.fragments.detailsfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.chiorerickandmorty.data.model.Characters
import com.example.chiorerickandmorty.repository.DetilsRepository
import com.example.chiorerickandmorty.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: DetilsRepository,
) : ViewModel() {

    private val _characterDetailsData = MutableLiveData<Resource<Characters>>(Resource.Loading())
    val detailsCharacter: LiveData<Resource<Characters>> = _characterDetailsData

    fun characterById(characterId: Int) {
        viewModelScope.launch {
            val response = repository.getCharacterById(characterId)
            response.data?.let {
                _characterDetailsData.value = Resource.Success(it)
            }
        }
    }

    val listEpisodes = repository.getAllEpisodes().cachedIn(viewModelScope)

}