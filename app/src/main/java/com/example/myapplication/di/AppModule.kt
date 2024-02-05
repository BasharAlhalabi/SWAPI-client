package com.example.myapplication.di

import com.example.myapplication.data.repository.CharacterRepository
import com.example.myapplication.network.SwApiService
import com.example.myapplication.network.SwApiServiceImpl
import com.example.myapplication.usecase.GetStarWarsCharactersUseCase
import com.example.myapplication.viewmodel.StarWarsCharacterViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { CharacterRepository(get()) }
    single { GetStarWarsCharactersUseCase(get()) }
    viewModel { StarWarsCharacterViewModel(get()) }
}

val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                level = LogLevel.BODY
            }
        }
    }
    single<SwApiService> { SwApiServiceImpl(get()) }
}


