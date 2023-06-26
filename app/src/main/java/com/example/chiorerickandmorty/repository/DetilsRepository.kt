package com.example.chiorerickandmorty.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.chiorerickandmorty.data.model.Characters
import com.example.chiorerickandmorty.data.remote.RickAndMortyApi
import com.example.chiorerickandmorty.paging.EpisodesPagingSource
import com.example.chiorerickandmorty.util.Resource
import java.lang.Exception
import javax.inject.Inject

class DetilsRepository @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi
) {

    suspend fun getCharacterById(characterId: Int): Resource<Characters> {
        return try {
            val response = rickAndMortyApi.getCharacterById(characterId)
            if (response.isSuccessful) {
                response.body()?.let {
                    return Resource.Success(it)
                } ?: Resource.Error("Response is null")
            } else {
                Resource.Error("Response is not successful")
            }
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    fun getAllEpisodes() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {EpisodesPagingSource(rickAndMortyApi)}
        ).liveData

}