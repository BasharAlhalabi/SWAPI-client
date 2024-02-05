package com.example.myapplication.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myapplication.network.StarWarsCharacterPagingSource
import com.example.myapplication.data.model.StarWarsCharacter
import com.example.myapplication.network.SwApiService
import kotlinx.coroutines.flow.Flow

class CharacterRepository(private val service: SwApiService) {
    fun getCharactersStream(): Flow<PagingData<StarWarsCharacter>> {
        return Pager(
            config = PagingConfig(
                pageSize = 15,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { StarWarsCharacterPagingSource(service) }
        ).flow
    }
}