package org.elsysbg.synthi.data.model

data class Library(
    val songs: List<Media> = emptyList(),
    val albums: Map<Long, List<Media>> = songs.groupBy { it.albumId },
    val artists: Map<Long, List<Media>> = songs.groupBy { it.artistId },
    val playlists: Map<Long, List<Media>> = emptyMap()
)