package com.example.chiorerickandmorty.domain.mappers


import com.example.chiorerickandmorty.data.model.Characters
import com.example.chiorerickandmorty.data.model.Result
import com.example.chiorerickandmorty.domain.models.Episode

object EpisodeMapper {

    fun buildFrom(
        networkEpiosde: Result,
        characters: List<Characters>
    ): Episode {
        return Episode(
            id = networkEpiosde.id,
            name = networkEpiosde.name,
            airDate = networkEpiosde.air_date,
            episode = networkEpiosde.episode,
            created = networkEpiosde.created,
            characters = characters.map {
                CharachterMapper.buildFrom(it, emptyList())
            }
        )
    }

}