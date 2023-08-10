package com.example.chiorerickandmorty.domain.mappers

import CharachterMapper
import com.example.chiorerickandmorty.data.model.Characters
import com.example.chiorerickandmorty.data.model.location.Result
import com.example.chiorerickandmorty.domain.models.Location

object LocationMapper {

    fun buildFrom(
        networkLocation: Result,
        characters: List<Characters>
    ): Location {
        return Location(
            id = networkLocation.id,
            name = networkLocation.name,
            type = networkLocation.type,
            dimension = networkLocation.dimension,
            created = networkLocation.created,
            residents = characters.map {
                CharachterMapper.buildFrom(it, emptyList())
            }
        )
    }
}