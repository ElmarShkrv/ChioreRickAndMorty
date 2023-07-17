package com.example.chiorerickandmorty.ui.fragments.episodecharctersbottomsheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chiorerickandmorty.domain.models.Episode
import com.example.chiorerickandmorty.repository.EpisodeCharactersRepository
import com.example.chiorerickandmorty.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeCharactersViewModel @Inject constructor(
    private val repository: EpisodeCharactersRepository,
) : ViewModel() {

    private val _characterByEpisodes = MutableLiveData<Resource<Episode?>>(Resource.Loading())
    val characterByEpisodes: LiveData<Resource<Episode?>> = _characterByEpisodes

    init {
        _characterByEpisodes.value = Resource.Loading()
    }

    fun getCharachetrByEpisode(episodeId: Int) {

        viewModelScope.launch {
            val response = repository.getEpiosdeByIdForCharachters(episodeId)

            _characterByEpisodes.value = Resource.Success(response)
        }
    }

}