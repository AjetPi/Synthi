package org.elsysbg.synthi.data.repository

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.annotation.WorkerThread
import dagger.hilt.android.qualifiers.ApplicationContext
import org.elsysbg.synthi.data.model.Media
import javax.inject.Inject

class MediaHelper @Inject constructor(@ApplicationContext val context: Context) {
    private val collection: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    private val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.DISPLAY_NAME,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ARTIST_ID,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.ALBUM_ID,
        MediaStore.Audio.Media.ALBUM,
        MediaStore.Audio.Media.TRACK
    )
    private val selection = "${MediaStore.Audio.Media.IS_MUSIC} = ?"
    private val selectionArgs = arrayOf("1")
    private val sortOrder = "${projection[0]} ASC"

    @WorkerThread
    fun getMedias(): List<Media> {
        val medias = mutableListOf<Media>()

        val query = context.contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )
        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(projection[0])
            val displayNameColumn = cursor.getColumnIndexOrThrow(projection[1])
            val durationColumn = cursor.getColumnIndexOrThrow(projection[2])
            val titleColumn = cursor.getColumnIndexOrThrow(projection[3])
            val artistIdColumn = cursor.getColumnIndexOrThrow(projection[4])
            val artistColumn = cursor.getColumnIndexOrThrow(projection[5])
            val albumIdColumn = cursor.getColumnIndexOrThrow(projection[6])
            val albumColumn = cursor.getColumnIndexOrThrow(projection[7])
            val trackColumn = cursor.getColumnIndexOrThrow(projection[8])

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val displayName = cursor.getString(displayNameColumn)
                val duration = cursor.getInt(durationColumn)
                val title = cursor.getString(titleColumn)
                val artistId = cursor.getLong(artistIdColumn)
                val artist = cursor.getString(artistColumn)
                val albumId = cursor.getLong(albumIdColumn)
                val album = cursor.getString(albumColumn)
                val track = cursor.getInt(trackColumn)
                val contentUri: Uri = ContentUris.withAppendedId(collection, id)

                medias += Media(
                    contentUri,
                    id,
                    displayName,
                    duration,
                    title,
                    artistId,
                    artist,
                    albumId,
                    album,
                    track
                )
            }
        }

        return medias
    }
}