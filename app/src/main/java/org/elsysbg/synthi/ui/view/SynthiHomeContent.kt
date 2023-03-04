package org.elsysbg.synthi.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.elsysbg.synthi.ui.SynthiUiState
import org.elsysbg.synthi.data.model.Category
import org.elsysbg.synthi.data.model.Media

@Composable
fun SynthiHomeContent(
    uiState: SynthiUiState,
    category: Category,
    onItemClick: (Media) -> Unit,
    onListClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    uiState.apply {
        when (category) {
            Category.Songs -> {
                val medias = library.songs.filter { it.title.contains(search ?: "", ignoreCase = true) }
                LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {
                    items(medias) { media ->
                        SynthiAudioItem(
                            category = category,
                            title = media.title,
                            subtitle = media.artist,
                            onItemClick = { onItemClick(media) }
                        )
                    }
                }
            }
            Category.Albums -> {
                val albums = library.albums.filter { it.title.contains(search ?: "", ignoreCase = true) }
                LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {
                    items(albums) { album ->
                        SynthiAudioItem(
                            category = category,
                            title = album.title,
                            subtitle = album.artist,
                            onItemClick = { onListClick(album.id) }
                        )
                    }
                }
            }
            Category.Artists -> {
                val artists = library.artists.filter { it.name.contains(search ?: "", ignoreCase = true) }
                LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {
                    items(artists) { artist ->
                        SynthiAudioItem(
                            category = category,
                            title = artist.name,
                            subtitle = "",
                            onItemClick = { onListClick(artist.id) }
                        )
                    }
                }
            }
            Category.Playlists -> {
                LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {
                    items(library.playlists) { playlist ->
                        SynthiAudioItem(
                            category = category,
                            title = playlist.name,
                            subtitle = "",
                            onItemClick = { onListClick(playlist.id) }
                        )
                    }
                }
            }
        }
    }
}