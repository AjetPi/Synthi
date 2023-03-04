package org.elsysbg.synthi.data.model

data class Library(
    val songs: List<Media> = emptyList(),
    val albums: List<Album> = emptyList(),
    val artists: List<Artist> = emptyList(),
    val playlists: List<Playlist> = emptyList()
)