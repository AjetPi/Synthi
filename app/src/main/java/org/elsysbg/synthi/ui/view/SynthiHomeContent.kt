package org.elsysbg.synthi.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.RequestManager
import org.elsysbg.synthi.ui.Category
import org.elsysbg.synthi.data.model.Library
import org.elsysbg.synthi.data.model.Media

@Composable
fun SynthiHomeContent(
    library: Library,
    search: String?,
    category: Category,
    onItemClick: (Media) -> Unit,
    onListClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    requestManager: RequestManager
) {
    when (category) {
        Category.Songs -> {
            val songs = library.songs.filter { it.title.contains(search ?: "", ignoreCase = true) }
            LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {
                items(songs) { media ->
                    SynthiAudioItem(
                        requestManager = requestManager,
                        coverUri = media.coverUri,
                        imageVector = category.icon,
                        title = media.title,
                        subtitle = media.artist,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { onItemClick(media) }
                    )
                }
            }
        }
        Category.Albums -> {
            val albums = library.albums.filter { it.title.contains(search ?: "", ignoreCase = true) }
            LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {
                items(albums) { album ->
                    SynthiAudioItem(
                        requestManager = requestManager,
                        coverUri = album.coverUri,
                        imageVector = category.icon,
                        title = album.title,
                        subtitle = album.artist,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { onListClick(album.id) }
                    )
                }
            }
        }
        Category.Artists -> {
            val artists = library.artists.filter { it.name.contains(search ?: "", ignoreCase = true) }
            LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {
                items(artists) { artist ->
                    SynthiAudioItem(
                        requestManager = requestManager,
                        coverUri = null,
                        imageVector = category.icon,
                        title = artist.name,
                        subtitle = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { onListClick(artist.id) }
                    )
                }
            }
        }
        Category.Playlists -> {
            val playlists = library.playlists.filter { it.name.contains(search ?: "", ignoreCase = true) }
            LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {
                items(playlists) { playlist ->
                    SynthiAudioItem(
                        requestManager = requestManager,
                        coverUri = null,
                        imageVector = category.icon,
                        title = playlist.name,
                        subtitle = null,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { onListClick(playlist.id) }
                    )
                }
            }
        }
    }
}