package com.example.chiorerickandmorty.ui.fragments.homefragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.chiorerickandmorty.data.model.Characters
import com.example.chiorerickandmorty.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
) : ViewModel() {

    private val _test = MutableLiveData<PagingData<Characters>>()
    val test: LiveData<PagingData<Characters>> = _test

    var filterValue = MutableLiveData<Array<Int>>()

    init {
        filterValue.value = arrayOf(0, 0)
    }

    fun getAllCharacters(): LiveData<PagingData<Characters>> {
        val response = repository.getAllCharacters().cachedIn(viewModelScope)
        _test.value = response.value
        return response
    }

    fun getByStatusAndGender(status: String, gender: String): LiveData<PagingData<Characters>> {
        val response =
            repository.getCharactersbyStatusAndGender(status, gender).cachedIn(viewModelScope)
        _test.value = response.value
        return response
    }

    fun getByStatus(status: String): LiveData<PagingData<Characters>> {
        val response = repository.getCharactersByStatus(status).cachedIn(viewModelScope)
        _test.value = response.value
        return response
    }

    fun getByGender(gender: String): LiveData<PagingData<Characters>> {
        val response = repository.getCharactersByGender(gender).cachedIn(viewModelScope)
        _test.value = response.value
        return response
    }


//     fun getAllCharacters() {
//        viewModelScope.launch {
//            val charactersData = repository.getAllCharacters().cachedIn(viewModelScope)
//            _test.value = charactersData.value
//        }
//    }
//
//    fun getByStatusAndGender(status: String, gender: String) {
//        viewModelScope.launch {
//            val charactersData = repository.getCharactersbyStatusAndGender(
//                status, gender
//            ).cachedIn(viewModelScope)
//            _test.value = charactersData.value
//        }
//    }
//
//    fun getByStatus(status: String) {
//        viewModelScope.launch {
//            val charactersData =
//                repository.getCharactersByStatus(status).cachedIn(viewModelScope)
//
//            _test.value = charactersData.value
//        }
//    }
//
//    fun getByGender(gender: String) {
//        viewModelScope.launch {
//            val charactersData =
//                repository.getCharactersByGender(gender).cachedIn(viewModelScope)
//
//            _test.value = charactersData.value
//        }
//    }

}