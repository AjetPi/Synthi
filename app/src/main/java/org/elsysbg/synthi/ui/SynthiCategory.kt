package org.elsysbg.synthi.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.elsysbg.synthi.data.model.Category
import org.elsysbg.synthi.data.model.Media

@Composable
fun SynthiCategory(
    synthiUiState: SynthiUiState,
    modifier: Modifier = Modifier,
    onItemClick: (Media) -> Unit
) {
    if (synthiUiState.library.songs.isNotEmpty()) {
        when (synthiUiState.currentCategory) {
            Category.Songs -> {
                LazyColumn(modifier = modifier.padding(16.dp)) {
                    items(synthiUiState.library.songs) { audio ->
                        SynthiItem(category = synthiUiState.currentCategory, title = audio.title, label = audio.artist, onItemClick = { onItemClick(audio) })
                    }
                }
            }
            Category.Albums -> {
                /*LazyColumn(modifier = modifier.padding(16.dp)) {
                    items(synthiUiState.library.albums.keys.toList()) {
                        val first = synthiUiState.library.albums[it]!!.first()
                        SynthiItem(category = synthiUiState.currentCategory, title = first.album, label = first.artist)
                    }
                }*/
            }
            Category.Artists -> {
                /*LazyColumn(modifier = modifier.padding(16.dp)) {
                    items(synthiUiState.library.artists.keys.toList()) {
                        val first = synthiUiState.library.artists[it]!!.first()
                        SynthiItem(category = synthiUiState.currentCategory, title = first.artist, label = "")
                    }
                }*/
            }
            Category.Playlists -> {}
        }
    }
}

@Composable
fun SynthiItem(
    category: Category,
    title: String,
    label: String,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable {
                onItemClick()
            }
    ) {
        Row {
            Icon(
                imageVector = category.icon,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)
            )
            Column(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .align(alignment = Alignment.CenterVertically)
            ) {
                Text(
                    text = title,
                    modifier = Modifier.padding(bottom = 2.dp),
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    }
}