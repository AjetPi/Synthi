package org.elsysbg.synthi.data.model

import android.net.Uri

data class Album(
    val songs: List<Media>
) {
    private val first: Media
        get() = songs.first()

    val id: Long
        get() = first.albumId
    val duration: Int
        get() = songs.sumOf { it.duration }
    val title: String
        get() = first.album
    val artistId: Long
        get() = first.artistId
    val artist: String
        get() = first.artist
    val coverUri: Uri
        get() = first.coverUri
}