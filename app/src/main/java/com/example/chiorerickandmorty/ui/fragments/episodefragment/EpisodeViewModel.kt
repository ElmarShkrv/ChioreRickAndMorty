package com.example.chiorerickandmorty.ui.fragments.episodefragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.chiorerickandmorty.repository.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val repository: EpisodeRepository,
) : ViewModel() {

    val allEpisodes = repository.getAllEpisodes().cachedIn(viewModelScope)

}