package com.example.chiorerickandmorty.data.remote

import com.example.chiorerickandmorty.data.model.CharacterResponse
import com.example.chiorerickandmorty.data.model.Characters
import com.example.chiorerickandmorty.data.model.EpisodesResponse
import com.example.chiorerickandmorty.data.model.Result
import com.example.chiorerickandmorty.domain.models.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character/")
    suspend fun getAllCharacters(
        @Query("status") status: String?,
        @Query("gender") gender: String?,
        @Query("page") page: Int? = null,
    ): Response<CharacterResponse>

    @GET("character/{character-id}")
    suspend fun getCharacterById(
        @Path("character-id") characterId : Int
    ): Response<Characters>

    @GET("episode/")
    suspend fun getAllEpisodes(
        @Query("page") page: Int? = null
    ) : Response<EpisodesResponse>

    @GET("episode/{episode-range}")
    suspend fun getEpisodeRange(
        @Path("episode-range") episodeId: String
    ): Response<List<Result>>

}