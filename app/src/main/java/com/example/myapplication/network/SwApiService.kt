package com.example.myapplication.network

import com.example.myapplication.data.model.StarWarsCharacterListResponse

interface SwApiService {
    suspend fun getStarWarsCharacters(page: Int): StarWarsCharacterListResponse
}