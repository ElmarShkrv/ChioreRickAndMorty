package com.example.chiorerickandmorty.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.chiorerickandmorty.data.model.CharacterResponse
import com.example.chiorerickandmorty.data.remote.RickAndMortyApi
import com.example.chiorerickandmorty.paging.HomeFragmentPagingSource
import retrofit2.Response
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi
) {

    fun getAllCharacters() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { HomeFragmentPagingSource(rickAndMortyApi, null, null) }
        ).liveData

    fun getCharactersbyStatusAndGender(status: String, gender: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { HomeFragmentPagingSource(rickAndMortyApi, status, gender) }
        ).liveData

    fun getCharactersByStatus(status: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { HomeFragmentPagingSource(rickAndMortyApi, status, null) }
        ).liveData

    fun getCharactersByGender(gender: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { HomeFragmentPagingSource(rickAndMortyApi, null, gender) }
        ).liveData
}