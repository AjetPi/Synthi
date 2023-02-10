package org.elsys.synthi.data.model

import android.net.Uri

data class Audio(
    val contentUri: Uri,
    val id: Long,
    val displayName: String,
    val duration: Int,
    val title: String,
    val artistId: Long,
    val artist: String,
    val albumId: Long,
    val album: String
)