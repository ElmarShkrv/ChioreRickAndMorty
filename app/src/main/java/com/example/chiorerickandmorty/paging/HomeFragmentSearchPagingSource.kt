package com.example.chiorerickandmorty.paging

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.chiorerickandmorty.data.model.Characters
import com.example.chiorerickandmorty.data.remote.RickAndMortyApi
import com.example.chiorerickandmorty.util.Constants.Companion.STARTING_PAGE_INDEX
import kotlinx.coroutines.delay

class HomeFragmentSearchPagingSource(
    private val rickAndMortyApi: RickAndMortyApi,
    private val name: String?,

) : PagingSource<Int, Characters>() {

    override fun getRefreshKey(state: PagingState<Int, Characters>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Characters> {
        val pageNumber = params.key ?: 1
        return try {
            delay(1_500)
            val response = rickAndMortyApi.getCharactersBySearch(pageNumber, name)
            val pagedResponse = response.body()
            val data = pagedResponse?.results

            var nextPageNumber: Int? = null
            if (pagedResponse?.info?.next != null) {
                val uri = Uri.parse(pagedResponse.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}