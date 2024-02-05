package com.example.myapplication.data.model

import kotlinx.serialization.Serializable

@Serializable
data class StarWarsCharacterListResponse(
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<StarWarsCharacter>? = null
)