package org.elsys.synthi.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.elsys.synthi.data.Category

@Composable
fun SynthiCategory(
    synthiUiState: SynthiUiState,
    modifier: Modifier = Modifier
) {
    if (synthiUiState.library.isNotEmpty()) {
        when (synthiUiState.currentCategory) {
            Category.Songs -> {
                LazyColumn(modifier = modifier.padding(16.dp)) {
                    items(synthiUiState.library) {
                        SynthiItem(category = synthiUiState.currentCategory, title = it.title, label = it.artist)
                    }
                }
            }
            Category.Albums -> {
                val albums = synthiUiState.library.groupBy { it.album }.keys.toList()
                LazyColumn(modifier = modifier.padding(16.dp)) {
                    items(albums) {
                        SynthiItem(category = synthiUiState.currentCategory, title = it, label = null)
                    }
                }
            }
            Category.Artists -> {
                val artists = synthiUiState.library.groupBy { it.artist }.keys.toList()
                LazyColumn(modifier = modifier.padding(16.dp)) {
                    items(artists) {
                        SynthiItem(category = synthiUiState.currentCategory, title = it, label = null)
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
    label: String?,
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
                style = MaterialTheme.typography.titleMedium
            )
            if (label != null) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}