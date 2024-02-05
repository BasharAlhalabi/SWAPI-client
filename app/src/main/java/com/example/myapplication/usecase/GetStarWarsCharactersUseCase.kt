package com.example.myapplication.usecase

import com.example.myapplication.data.repository.CharacterRepository

class GetStarWarsCharactersUseCase(private val repository: CharacterRepository) {
    operator fun invoke() = repository.getCharactersStream()
}