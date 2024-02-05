package com.example.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.example.myapplication.R
import com.example.myapplication.data.model.StarWarsCharacter
import com.example.myapplication.ui.theme.DarkGray
import com.example.myapplication.ui.theme.StarJediFont
import com.example.myapplication.ui.theme.White
import com.example.myapplication.viewmodel.StarWarsCharacterViewModel
import kotlinx.coroutines.flow.map


@Composable
fun StarWarsCharacterListScreen(
    viewModel: StarWarsCharacterViewModel,
    onCharacterClick: (StarWarsCharacter) -> Unit
) {

    val nonNullableCharactersFlow = remember(viewModel.characters) {
        viewModel.characters.map { it ?: PagingData.empty() }
    }

    val characters = nonNullableCharactersFlow.collectAsLazyPagingItems()

    LazyColumn(modifier = Modifier.fillMaxWidth()) {

        items(characters) { character ->
            character?.let {
                CharacterItem(character = it, onCharacterClick = onCharacterClick)
            }
        }

        when {
            characters.loadState.append is LoadState.Loading -> {
                item { LoadingItem() }
            }

            characters.loadState.refresh is LoadState.Loading -> {
                item { FullScreenLoading() }
            }

            characters.loadState.refresh is LoadState.Error -> {
                item { FullScreenError(onRetry = { characters.retry() }) }
            }
        }
    }
}

@Composable
fun FullScreenLoading() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(color = DarkGray)
    }
}

@Composable
fun FullScreenError(onRetry: () -> Unit) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.text_failed_to_load),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onRetry) {
                Text(stringResource(R.string.text_retry))
            }
        }
    }
}

@Composable
fun LoadingItem() {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        CircularProgressIndicator(color = DarkGray)
    }
}

@Composable
fun CharacterItem(
    character: StarWarsCharacter,
    onCharacterClick: (StarWarsCharacter) -> Unit
) {
    val painter = rememberImagePainter(
        data = character.imageUrl,
        builder = {
            placeholder(R.drawable.ic_placeholder)
            error(R.drawable.ic_placeholder_error)
        }
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCharacterClick(character) },
        colors = CardDefaults.cardColors(containerColor = DarkGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkGray)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(1.dp, White, CircleShape)
            ) {
                Image(
                    painter = painter,
                    contentDescription = stringResource(R.string.text_star_wars_character_image),
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "${character.name}, ${character.birthYear}",
                style = MaterialTheme.typography.bodyLarge,
                color = White,
                fontFamily = StarJediFont
            )
        }
    }
}
