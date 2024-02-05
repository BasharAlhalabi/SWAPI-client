package com.example.myapplication.network

import com.example.myapplication.data.model.StarWarsCharacterListResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class SwApiServiceImpl(private val httpClient: HttpClient) : SwApiService {
    override suspend fun getStarWarsCharacters(page: Int): StarWarsCharacterListResponse {
        return httpClient.get("https://swapi.dev/api/people?page=$page").body()
    }
}
