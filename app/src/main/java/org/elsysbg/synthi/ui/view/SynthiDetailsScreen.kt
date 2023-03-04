package org.elsysbg.synthi.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.elsysbg.synthi.data.model.Category
import org.elsysbg.synthi.data.model.Library
import org.elsysbg.synthi.data.model.Media

@Composable
fun SynthiDetailsScreen(
    library: Library,
    category: Category,
    listId: Long,
    onItemClick: (Media) -> Unit,
    modifier: Modifier = Modifier
) {
    when (category) {
        Category.Albums -> {
            val album = library.albums.find { it.id == listId }!!
            SynthiDetailsContent(
                list = album.songs,
                category = category,
                title = album.title,
                onItemClick = onItemClick,
                modifier = modifier
            )
        }
        Category.Artists -> {
            val artist = library.artists.find { it.id == listId }!!
            SynthiDetailsContent(
                list = artist.songs,
                category = category,
                title = artist.name,
                onItemClick = onItemClick,
                modifier = modifier
            )
        }
        Category.Playlists -> SynthiDetailsContent(
            list = library.playlists.find { it.id == listId }!!.members,
            category = category,
            title = library.playlists.find { it.id == listId }!!.name,
            onItemClick = onItemClick,
            modifier = modifier
        )
        else -> {}
    }
}