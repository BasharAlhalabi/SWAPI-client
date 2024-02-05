package com.example.myapplication.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.data.model.StarWarsCharacter

class StarWarsCharacterPagingSource(
    private val swApiService: SwApiService
) : PagingSource<Int, StarWarsCharacter>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StarWarsCharacter> {
        return try {
            val pageNumber = params.key ?: 1
            val response = swApiService.getStarWarsCharacters(page = pageNumber)

            val data = response.results?.map { apiCharacter ->
                appendImageUrl(apiCharacter)
            }

            LoadResult.Page(
                data = data ?: emptyList(),
                prevKey = null,
                nextKey = if (response.next == null) null else pageNumber + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, StarWarsCharacter>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    private fun appendImageUrl(apiCharacter: StarWarsCharacter): StarWarsCharacter {
        val characterNumber = apiCharacter.url?.dropLast(1)?.takeLastWhile { it.isDigit() }
        val imageUrl = characterNumber?.let{
            "https://vieraboschkova.github.io/swapi-gallery/static/assets/img/people/$it.jpg"
        }
        return apiCharacter.copy(imageUrl = imageUrl)
    }
}