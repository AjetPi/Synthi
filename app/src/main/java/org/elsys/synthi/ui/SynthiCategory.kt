package org.elsys.synthi.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.elsys.synthi.data.model.Category

@Composable
fun SynthiCategory(
    synthiUiState: SynthiUiState,
    modifier: Modifier = Modifier
) {
    if (synthiUiState.library.songs.isNotEmpty()) {
        when (synthiUiState.currentCategory) {
            Category.Songs -> {
                LazyColumn(modifier = modifier.padding(16.dp)) {
                    items(synthiUiState.library.songs) {
                        SynthiItem(category = synthiUiState.currentCategory, title = it.title, label = it.artist)
                    }
                }
            }
            Category.Albums -> {
                LazyColumn(modifier = modifier.padding(16.dp)) {
                    items(synthiUiState.library.albums.keys.toList()) {
                        val first = synthiUiState.library.albums[it]!!.first()
                        SynthiItem(category = synthiUiState.currentCategory, title = first.album, label = first.artist)
                    }
                }
            }
            Category.Artists -> {
                LazyColumn(modifier = modifier.padding(16.dp)) {
                    items(synthiUiState.library.artists.keys.toList()) {
                        val first = synthiUiState.library.artists[it]!!.first()
                        SynthiItem(category = synthiUiState.currentCategory, title = first.artist, label = "")
                    }
                }
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
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
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