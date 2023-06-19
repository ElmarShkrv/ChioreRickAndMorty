package com.example.chiorerickandmorty.ui.fragments.homefragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.chiorerickandmorty.repository.HomeRepository
import com.example.chiorerickandmorty.util.DataFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
) : ViewModel() {

    var isFilter = MutableLiveData<Boolean>()

    init {
        isFilter.value = false
    }

    private val _filter = MutableLiveData<DataFilter>()

    fun setFilter(filter: DataFilter) {
        _filter.value = filter
    }

    val charactersData = Transformations.switchMap(_filter) { filter ->
        when(filter) {
            DataFilter.All -> repository.getAllCharacters().also { isFilter.value = false }
            is DataFilter.StatusAndGender -> repository.getCharactersbyStatusAndGender(filter.status, filter.gender).also { isFilter.value = true }
            is DataFilter.Status -> repository.getCharactersByStatus(filter.status).also { isFilter.value = true }
            is DataFilter.Gender -> repository.getCharactersByGender(filter.gender).also { isFilter.value = true }
        }.cachedIn(viewModelScope)
    }

//    fun getAllCharacters(): LiveData<PagingData<Characters>> {
//        val response = repository.getAllCharacters().cachedIn(viewModelScope)
//        test = response as MutableLiveData<PagingData<Characters>>
//        return response
//    }
//
//    fun getByStatusAndGender(status: String, gender: String): LiveData<PagingData<Characters>> {
//        val response =
//            repository.getCharactersbyStatusAndGender(status, gender).cachedIn(viewModelScope)
//        test = response as MutableLiveData<PagingData<Characters>>
//        return response
//    }
//
//    fun getByStatus(status: String): LiveData<PagingData<Characters>> {
//        val response = repository.getCharactersByStatus(status).cachedIn(viewModelScope)
//        test = response as MutableLiveData<PagingData<Characters>>
//        return response
//    }
//
//    fun getByGender(gender: String): LiveData<PagingData<Characters>> {
//        val response = repository.getCharactersByGender(gender).cachedIn(viewModelScope)
//        test = response as MutableLiveData<PagingData<Characters>>
//        return response
//    }


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