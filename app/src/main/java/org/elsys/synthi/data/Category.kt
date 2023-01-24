package org.elsys.synthi.data

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.ui.graphics.vector.ImageVector
import org.elsys.synthi.R

enum class Category(
    @StringRes val id: Int,
    val icon: ImageVector
) {
    Songs(
        id = R.string.songs,
        icon = Icons.Default.Audiotrack
    ),
    Albums(
        id = R.string.albums,
        icon = Icons.Default.Album
    ),
    Artists(
        id = R.string.artists,
        icon = Icons.Default.Person
    ),
    Playlists(
        id = R.string.playlists,
        icon = Icons.Default.QueueMusic
    )
}