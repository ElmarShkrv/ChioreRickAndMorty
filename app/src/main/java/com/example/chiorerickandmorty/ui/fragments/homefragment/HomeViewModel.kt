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
import retrofit2.Response
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
) : ViewModel() {

     var test = MutableLiveData<PagingData<Characters>>()


    var filterValue = MutableLiveData<Array<Int>>()

    init {
        filterValue.value = arrayOf(0, 0)
    }

    fun getAllCharacters() {
        val charactersData = repository.getAllCharacters().cachedIn(viewModelScope)

        test = charactersData as MutableLiveData<PagingData<Characters>>
    }

    fun getByStatusAndGender(status: String, gender: String) {
        viewModelScope.launch {
            val charactersData = repository.getCharactersbyStatusAndGender(
                status, gender
            ).cachedIn(viewModelScope)
            test = charactersData as MutableLiveData<PagingData<Characters>>
        }
    }

    fun getByStatus(status: String) {
        viewModelScope.launch {
            val charactersData =
                repository.getCharactersByStatus(status).cachedIn(viewModelScope)

            test = charactersData as MutableLiveData<PagingData<Characters>>
        }
    }

    fun getByGender(gender: String) {
        viewModelScope.launch {
            val charactersData =
                repository.getCharactersByGender(gender).cachedIn(viewModelScope)

            test = charactersData as MutableLiveData<PagingData<Characters>>
        }
    }

}