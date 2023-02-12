package org.elsys.synthi.data.model

data class Library(
    val songs: List<Audio> = emptyList(),
    val albums: Map<Long, List<Audio>> = songs.groupBy { it.albumId },
    val artists: Map<Long, List<Audio>> = songs.groupBy { it.artistId },
    val playlists: Map<Long, List<Audio>> = emptyMap()
)