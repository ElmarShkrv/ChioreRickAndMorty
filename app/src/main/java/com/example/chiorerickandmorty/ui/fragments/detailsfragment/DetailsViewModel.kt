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
import com.example.chiorerickandmorty.domain.models.Character
import com.example.chiorerickandmorty.domain.models.Episode
import kotlinx.coroutines.delay

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



    private val _characterEpisodes = MutableLiveData<Resource<Character?>>(Resource.Loading())
    val characterEpisodes: LiveData<Resource<Character?>> = _characterEpisodes

    fun getCharacterEpisodes(characterId: Int) {

        viewModelScope.launch{
            val response = repository.getCharacterByIdForEpisodes(characterId)

//            _characterEpisodes.postValue(response)
            _characterEpisodes.value = Resource.Success(response)
        }
    }

    private val _characterByEpisodes = MutableLiveData<Resource<Episode?>>(Resource.Loading())
    val characterByEpisodes: LiveData<Resource<Episode?>> = _characterByEpisodes

    fun getCharachetrByEpisode(episodeId: Int) {

        viewModelScope.launch {
            val response = repository.getEpiosdeByIdForCharachters(episodeId)

            _characterByEpisodes.value = Resource.Success(response)
        }
    }


    init {
        _characterEpisodes.value = Resource.Loading()
        _characterByEpisodes.value = Resource.Loading()
    }



}