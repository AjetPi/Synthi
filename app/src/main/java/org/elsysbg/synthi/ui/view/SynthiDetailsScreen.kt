package org.elsysbg.synthi.ui.view

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumptech.glide.RequestManager
import org.elsysbg.synthi.ui.Category
import org.elsysbg.synthi.data.model.Library
import org.elsysbg.synthi.data.model.Media

@Composable
fun SynthiDetailsScreen(
    library: Library,
    category: Category,
    listId: Long,
    onItemClick: (Media) -> Unit,
    modifier: Modifier = Modifier,
    requestManager: RequestManager
) {
    when (category) {
        Category.Albums -> {
            val album = library.albums.find { it.id == listId }!!
            SynthiDetailsContent(
                list = album.songs,
                coverUri = album.coverUri,
                icon = category.icon,
                requestManager = requestManager,
                title = album.title,
                onItemClick = onItemClick,
                modifier = modifier
            )
        }
        Category.Artists -> {
            val artist = library.artists.find { it.id == listId }!!
            SynthiDetailsContent(
                list = artist.songs,
                coverUri = Uri.EMPTY,
                icon = category.icon,
                requestManager = requestManager,
                title = artist.name,
                onItemClick = onItemClick,
                modifier = modifier
            )
        }
        Category.Playlists -> SynthiDetailsContent(
            list = library.playlists.find { it.id == listId }!!.members,
            coverUri = Uri.EMPTY,
            icon = category.icon,
            requestManager = requestManager,
            title = library.playlists.find { it.id == listId }!!.name,
            onItemClick = onItemClick,
            modifier = modifier
        )
        else -> Unit
    }
}