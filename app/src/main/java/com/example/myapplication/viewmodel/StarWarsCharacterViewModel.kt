package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.data.model.StarWarsCharacter
import com.example.myapplication.usecase.GetStarWarsCharactersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class StarWarsCharacterViewModel(
    getStarWarsCharactersUseCase: GetStarWarsCharactersUseCase
) : ViewModel() {
    private val _selectedCharacter = MutableStateFlow<StarWarsCharacter?>(null)
    val selectedCharacter: StateFlow<StarWarsCharacter?> = _selectedCharacter.asStateFlow()

    private val _characters = MutableStateFlow<PagingData<StarWarsCharacter>?>(null)
    val characters: StateFlow<PagingData<StarWarsCharacter>?> = _characters.asStateFlow()

    fun selectCharacter(character: StarWarsCharacter) {
        _selectedCharacter.value = character
    }

    init {
        viewModelScope.launch {
            getStarWarsCharactersUseCase().cachedIn(viewModelScope).collectLatest {
                _characters.value = it
            }
        }
    }
}
