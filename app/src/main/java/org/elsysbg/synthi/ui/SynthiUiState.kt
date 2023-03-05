package org.elsysbg.synthi.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.ui.graphics.vector.ImageVector
import org.elsysbg.synthi.R
import org.elsysbg.synthi.data.model.Library

enum class Category(
    @StringRes val id: Int,
    val icon: ImageVector
) {
    Songs(id = R.string.songs, icon = Icons.Default.Audiotrack),
    Albums(id = R.string.albums, icon = Icons.Default.Album),
    Artists(id = R.string.artists, icon = Icons.Default.Person),
    Playlists(id = R.string.playlists, icon = Icons.Default.QueueMusic)
}

data class SynthiUiState(
    val library: Library = Library(),
    val search: String? = null,
    val topAppBarState: Boolean = true,
    val topSearchState: Boolean = false,
    val bottomPlayerState: Boolean = false,
    val bottomNavigationState: Boolean = true
)