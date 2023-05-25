package com.example.chiorerickandmorty.util

sealed class DataFilter {
    object All : DataFilter()
    data class StatusAndGender(val status: String, val gender: String) : DataFilter()
    data class Status(val status: String) : DataFilter()
    data class Gender(val gender: String) : DataFilter()
}
