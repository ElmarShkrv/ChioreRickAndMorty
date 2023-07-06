package com.example.chiorerickandmorty.domain.models


data class Episode(
    val id: Int = 0,
    val name: String = "",
    val airDate: String = "",
    val episode: String = "",
    val created: String = "",
    val characters: List<Character>
)