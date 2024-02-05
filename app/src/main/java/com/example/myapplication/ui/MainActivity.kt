package com.example.myapplication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.StarWarsCharacterViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationGraph()
                }
            }
        }
    }

    @Composable
    fun NavigationGraph() {
        val navController = rememberNavController()
        val viewModel: StarWarsCharacterViewModel = koinViewModel()

        NavHost(
            modifier = Modifier
                .statusBarsPadding(),
            navController = navController,
            startDestination = "characterList"
        ) {
            composable("characterList") {
                StarWarsCharacterListScreen(viewModel, onCharacterClick = {
                    viewModel.selectCharacter(it)
                    navController.navigate("characterDetail")
                })
            }
            composable("characterDetail") {
                StarWarsCharacterScreen(viewModel)
            }
        }
    }
}