package com.example.chiorerickandmorty.repository

import com.example.chiorerickandmorty.data.model.Characters
import com.example.chiorerickandmorty.data.model.location.Result
import com.example.chiorerickandmorty.data.remote.RickAndMortyApi
import com.example.chiorerickandmorty.domain.mappers.LocationMapper
import com.example.chiorerickandmorty.domain.models.Location
import javax.inject.Inject

class LocationCharactersRepository @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi
) {

    suspend fun getLocationByIdForCharachters(locationId: Int): Location? {
        val request = rickAndMortyApi.getLocationById(locationId)

        if (!request.isSuccessful) {
            return null
        }

        val networkCharacter = getCharacterFromLocationResponse(request.body()!!)
        return LocationMapper.buildFrom(
            networkLocation = request.body()!!,
            characters = networkCharacter
        )
    }

    private suspend fun getCharacterFromLocationResponse(
        locationResponse: Result
    ): List<Characters> {
        val characterRange = locationResponse.residents.map {
            it.substring(it.lastIndexOf("/") + 1)
        }.toString()

        val request = rickAndMortyApi.getCharacterRange(characterRange)

        if (!request.isSuccessful) {
            return emptyList()
        }

        return request.body()!!
    }

}