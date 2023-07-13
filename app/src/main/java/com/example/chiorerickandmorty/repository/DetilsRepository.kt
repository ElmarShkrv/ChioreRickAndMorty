package com.example.chiorerickandmorty.repository

import CharachterMapper
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.chiorerickandmorty.data.model.Characters
import com.example.chiorerickandmorty.data.model.Result
import com.example.chiorerickandmorty.data.remote.RickAndMortyApi
import com.example.chiorerickandmorty.domain.mappers.EpisodeMapper
import com.example.chiorerickandmorty.domain.models.Character
import com.example.chiorerickandmorty.domain.models.Episode
import com.example.chiorerickandmorty.paging.EpisodesPagingSource
import com.example.chiorerickandmorty.util.Resource
import kotlinx.coroutines.delay
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

    suspend fun getEpiosdeByIdForCharachters(episodeId: Int): Episode? {
        val request = rickAndMortyApi.getEpisodeById(episodeId)

        if (!request.isSuccessful) {
            return null
        }

        val networkCharacter = getCharacterFromEpisodeResponse(request.body()!!)
        return EpisodeMapper.buildFrom(
            networkEpiosde = request.body()!!,
            characters = networkCharacter
        )
    }

    private suspend fun getCharacterFromEpisodeResponse(
        epiosdeResponse: Result
    ): List<Characters> {
        val characterRange = epiosdeResponse.characters.map {
            it.substring(it.lastIndexOf("/") + 1)
        }.toString()

        val request = rickAndMortyApi.getCharacterRange(characterRange)

        if (!request.isSuccessful) {
            return emptyList()
        }

        return request.body()!!
    }

    suspend fun getCharacterByIdForEpisodes(characterId: Int): Character? {
        val request = rickAndMortyApi.getCharacterById(characterId)

        if (!request.isSuccessful) {
            return null
        }

        val networkEpiosde = getEpiosdeFromChaacterResponse(request.body()!!)
        return CharachterMapper.buildFrom(
            response = request.body()!!,
            episodes = networkEpiosde
        )
    }

    private suspend fun getEpiosdeFromChaacterResponse(
        characterResponse: Characters
    ): List<Result> {
        val episodeRange = characterResponse.episode.map {
            it.substring(it.lastIndexOf("/") + 1)
        }.toString()

        val request = rickAndMortyApi.getEpisodeRange(episodeRange)

        if (!request.isSuccessful) {
            return emptyList()
        }

        return request.body()!!
    }

//    fun getAllEpisodes() =
//        Pager(
//            config = PagingConfig(
//                pageSize = 20,
//                maxSize = 100,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = {EpisodesPagingSource(rickAndMortyApi)}
//        ).liveData

}