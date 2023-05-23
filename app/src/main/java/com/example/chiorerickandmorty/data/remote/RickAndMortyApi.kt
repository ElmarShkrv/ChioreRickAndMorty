package com.example.chiorerickandmorty.data.remote

import com.example.chiorerickandmorty.data.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character/")
    suspend fun getAllCharacters(
        @Query("status") status: String? = null,
        @Query("gender") gender: String? = null,
        @Query("page") page: Int? = null,
    ): Response<CharacterResponse>

}