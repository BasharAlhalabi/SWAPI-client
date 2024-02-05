package com.example.myapplication.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class StarWarsCharacter(
    @SerialName("birth_year")
    val birthYear: String? = null,
    val created: String? = null,
    val edited: String? = null,
    @SerialName("eye_color")
    val eyeColor: String? = null,
    val films: List<String>? = null,
    val gender: String? = null,
    @SerialName("hair_color")
    val hairColor: String? = null,
    val height: String? = null,
    @SerialName("homeworld")
    val homeWorld: String? = null,
    val mass: String? = null,
    val name: String? = null,
    @SerialName("skin_color")
    val skinColor: String? = null,
    val species: List<String>? = null,
    val starships: List<String>? = null,
    val url: String? = null,
    val vehicles: List<String>? = null,
    @Transient
    val imageUrl: String? = null
)