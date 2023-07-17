package com.example.chiorerickandmorty.repository

import com.example.chiorerickandmorty.data.model.Characters
import com.example.chiorerickandmorty.data.model.Result
import com.example.chiorerickandmorty.data.remote.RickAndMortyApi
import com.example.chiorerickandmorty.domain.mappers.EpisodeMapper
import com.example.chiorerickandmorty.domain.models.Episode
import javax.inject.Inject

class EpisodeCharactersRepository @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi
) {

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

}