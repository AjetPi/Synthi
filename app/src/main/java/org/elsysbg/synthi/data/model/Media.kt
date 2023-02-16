package org.elsysbg.synthi.data.model

import android.content.ContentUris
import android.net.Uri

data class Media(
    val contentUri: Uri,
    val id: Long,
    val displayName: String,
    val duration: Int,
    val title: String,
    val artistId: Long,
    val artist: String,
    val albumId: Long,
    val album: String,
    val track: Int,
    val coverUri: Uri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId)
)