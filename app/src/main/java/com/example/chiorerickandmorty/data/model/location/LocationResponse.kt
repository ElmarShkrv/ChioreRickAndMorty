package com.example.chiorerickandmorty.data.model.location

import com.example.chiorerickandmorty.data.model.Info

data class LocationResponse(
    val info: Info,
    val results: List<Result>
)