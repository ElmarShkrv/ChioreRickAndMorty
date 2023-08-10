package com.example.chiorerickandmorty.domain.models

class Location(
    val id: Int = 0,
    val name: String = "",
    val type: String = "",
    val dimension: String = "",
    val created: String = "",
    val residents: List<Character>
) {
}