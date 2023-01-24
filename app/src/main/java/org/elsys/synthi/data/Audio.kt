package org.elsys.synthi.data

import android.net.Uri

data class Audio(
    val uri: Uri,
    val title: String,
    val artist: String,
    val album: String,
    val track: Int,
    val duration: Int
)