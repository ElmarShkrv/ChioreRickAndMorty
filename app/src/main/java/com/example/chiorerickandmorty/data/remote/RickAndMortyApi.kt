package com.example.chiorerickandmorty.data.remote

import com.example.chiorerickandmorty.data.model.CharacterResponse
import com.example.chiorerickandmorty.data.model.Characters
import com.example.chiorerickandmorty.data.model.EpisodesResponse
import com.example.chiorerickandmorty.data.model.location.LocationResponse
import com.example.chiorerickandmorty.data.model.Result
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

    @GET("character/")
    suspend fun getCharactersBySearch(
        @Query("page") page: Int? = null,
        @Query("name") status: String?,
    ): Response<CharacterResponse>

    @GET("character/{character-id}")
    suspend fun getCharacterById(
        @Path("character-id") characterId : Int
    ): Response<Characters>

    @GET("episode/")
    suspend fun getAllEpisodes(
        @Query("page") page: Int? = null
    ) : Response<EpisodesResponse>

    @GET("location/")
    suspend fun getAllLocations(
        @Query("page") page: Int? = null
    ) : Response<LocationResponse>

    @GET("episode/{episode-range}")
    suspend fun getEpisodeRange(
        @Path("episode-range") episodeId: String
    ): Response<List<Result>>

    @GET("episode/{episode-id}")
    suspend fun getEpisodeById(
        @Path("episode-id") episodeId: Int
    ): Response<Result>

    @GET("character/{character-range}")
    suspend fun getCharacterRange(
        @Path("character-range") characterId: String
    ): Response<List<Characters>>

    @GET("location/{location-id}")
    suspend fun getLocationById(
        @Path("location-id") locationID: Int
    ): Response<com.example.chiorerickandmorty.data.model.location.Result>

}