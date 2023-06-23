package com.example.chiorerickandmorty.data.remote

import com.example.chiorerickandmorty.data.model.CharacterResponse
import com.example.chiorerickandmorty.data.model.Characters
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

}