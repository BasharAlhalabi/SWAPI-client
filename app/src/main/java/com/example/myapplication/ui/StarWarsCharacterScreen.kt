package com.example.myapplication.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.myapplication.R
import com.example.myapplication.data.model.StarWarsCharacter
import com.example.myapplication.ui.theme.StarJediFont
import com.example.myapplication.ui.theme.StarJholFont
import com.example.myapplication.ui.theme.White
import com.example.myapplication.viewmodel.StarWarsCharacterViewModel
import kotlinx.coroutines.launch

@Composable
fun StarWarsCharacterScreen(
    viewModel: StarWarsCharacterViewModel
) {
    val character by viewModel.selectedCharacter.collectAsState()


    character?.let {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DetailContent(character = it)
        }
    } ?: Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.fillMaxSize(),
            text = stringResource(R.string.text_empty_character_details),
            color = White,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = StarJholFont
        )
    }
}

@Composable
fun DetailContent(character: StarWarsCharacter) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val offsetY = remember { Animatable(initialValue = 1000f) }

    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            offsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 1000)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.text_character_details_title),
            color = MaterialTheme.colorScheme.outline,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = StarJholFont
        )
        Spacer(modifier = Modifier.height(16.dp))
        CharacterProperty(
            label = stringResource(R.string.text_name),
            value = character.name
        )
        CharacterProperty(
            label = stringResource(R.string.text_birth_year),
            value = character.birthYear
        )
        CharacterProperty(
            label = stringResource(R.string.text_gender),
            value = character.gender
        )
        CharacterProperty(
            label = stringResource(R.string.text_height),
            value = stringResource(R.string.text_unit_cm, character.height ?: "")
        )
        CharacterProperty(
            label = stringResource(R.string.text_mass), value = stringResource(
                R.string.text_unit_kg, character.mass ?: ""
            )
        )
        CharacterProperty(
            label = stringResource(R.string.text_eye_color),
            value = character.eyeColor
        )
        CharacterProperty(
            label = stringResource(R.string.text_hair_color),
            value = character.hairColor
        )

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(14.dp))

        val imageUrl = character.imageUrl ?: ""
        Image(
            painter = rememberImagePainter(data = imageUrl),
            contentDescription = stringResource(R.string.text_star_wars_character_image),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .graphicsLayer {
                    translationY = offsetY.value
                },
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun CharacterProperty(label: String, value: String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label: ",
            color =  MaterialTheme.colorScheme.secondary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = StarJholFont
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = value ?: stringResource(R.string.text_not_available),
            color =  MaterialTheme.colorScheme.secondary,
            fontSize = 15.sp,
            fontFamily = StarJediFont
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}