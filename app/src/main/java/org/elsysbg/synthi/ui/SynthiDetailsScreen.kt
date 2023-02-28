package org.elsysbg.synthi.ui

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
        Category.Albums -> SynthiDetailsContent(
            list = library.albums[listId]!!,
            category = category,
            title = library.albums[listId]!!.first().album,
            onItemClick = onItemClick,
            modifier = modifier
        )
        Category.Artists -> SynthiDetailsContent(
            list = library.artists[listId]!!,
            category = category,
            title = library.artists[listId]!!.first().artist,
            onItemClick = onItemClick,
            modifier = modifier
        )
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