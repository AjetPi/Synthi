package org.elsysbg.synthi.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                val albums = library.songs
                    .filter { it.album.contains(search ?: "", ignoreCase = true) }
                    .groupBy { it.albumId }
                LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {
                    items(albums.keys.toList()) { id ->
                        val media = albums[id]!!.first()
                        SynthiAudioItem(
                            category = category,
                            title = media.album,
                            subtitle = media.artist,
                            onItemClick = { onListClick(id) }
                        )
                    }
                }
            }
            Category.Artists -> {
                val artists = library.songs
                    .filter { it.artist.contains(search ?: "", ignoreCase = true) }
                    .groupBy { it.artistId }
                LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {
                    items(artists.keys.toList()) { id ->
                        val media = artists[id]!!.first()
                        SynthiAudioItem(
                            category = category,
                            title = media.artist,
                            subtitle = "",
                            onItemClick = { onListClick(id) }
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