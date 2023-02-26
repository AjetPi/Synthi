package org.elsysbg.synthi.data.repository

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.annotation.WorkerThread
import dagger.hilt.android.qualifiers.ApplicationContext
import org.elsysbg.synthi.data.model.Media
import org.elsysbg.synthi.data.model.Playlist
import javax.inject.Inject

class MediaHelper @Inject constructor(@ApplicationContext val context: Context) {
    private lateinit var collection: Uri
    private lateinit var projection: Array<String>
    private lateinit var selection: String
    private lateinit var selectionArgs: Array<String>
    private lateinit var sortOrder: String

    @WorkerThread
    fun getMedias(): List<Media> {
        collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        projection = arrayOf(
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
        selection = "${MediaStore.Audio.Media.IS_MUSIC} = ?"
        selectionArgs = arrayOf("1")
        sortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER

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

    @Suppress("DEPRECATION")
    @WorkerThread
    fun getPlaylists(): List<Playlist> {
        collection = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI
        projection = arrayOf(
            MediaStore.Audio.Playlists._ID,
            MediaStore.Audio.Playlists.NAME
        )
        sortOrder = MediaStore.Audio.Playlists.DEFAULT_SORT_ORDER

        val playlists = mutableListOf<Playlist>()

        val query = context.contentResolver.query(
            collection,
            projection,
            null,
            null,
            sortOrder
        )
        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(projection[0])
            val nameColumn = cursor.getColumnIndexOrThrow(projection[1])

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)

                playlists += Playlist(
                    id,
                    name
                )
            }
        }

        return playlists
    }
}