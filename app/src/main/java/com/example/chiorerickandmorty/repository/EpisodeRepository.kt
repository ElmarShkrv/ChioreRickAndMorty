package com.example.chiorerickandmorty.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.chiorerickandmorty.data.remote.RickAndMortyApi
import com.example.chiorerickandmorty.paging.EpisodesPagingSource
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi
) {

    fun getAllEpisodes() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { EpisodesPagingSource(rickAndMortyApi)}
        ).liveData

}