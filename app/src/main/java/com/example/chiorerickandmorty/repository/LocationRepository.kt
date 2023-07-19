package com.example.chiorerickandmorty.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.chiorerickandmorty.data.remote.RickAndMortyApi
import com.example.chiorerickandmorty.paging.LocationPagingSource
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi
) {

    fun getAllLocations() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { LocationPagingSource(rickAndMortyApi)}
        ).liveData

}