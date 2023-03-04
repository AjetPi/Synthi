package org.elsysbg.synthi.data.model

data class Artist(
    val albums: List<Album>
) {
    private val first: Album
        get() = albums.first()
    val songs: List<Media>
        get() = albums.flatMap { it.songs }

    val id: Long
        get() = first.artistId
    val duration: Int
        get() = albums.sumOf { it.duration }
    val name: String
        get() = first.artist
}