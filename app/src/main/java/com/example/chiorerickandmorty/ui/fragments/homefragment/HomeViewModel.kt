package com.example.chiorerickandmorty.ui.fragments.homefragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.chiorerickandmorty.data.model.Characters
import com.example.chiorerickandmorty.data.remote.RickAndMortyApi
import com.example.chiorerickandmorty.paging.HomeFragmentSearchPagingSource
import com.example.chiorerickandmorty.repository.HomeRepository
import com.example.chiorerickandmorty.util.DataFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val rickAndMortyApi: RickAndMortyApi
) : ViewModel() {

    private val currentQuery = MutableLiveData<String>()

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

    val searchResults: LiveData<PagingData<Characters>> = currentQuery.switchMap { query ->
        Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { HomeFragmentSearchPagingSource(rickAndMortyApi, query) }
        ).flow.cachedIn(viewModelScope).asLiveData()
    }

    fun setSearchQuery(query: String) {
        currentQuery.value = query
    }

    fun clearFilters() {
        _filter.value = DataFilter.All
        isFilter.value = false
    }

}