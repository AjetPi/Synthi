package org.elsysbg.synthi.data.repository

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import androidx.annotation.WorkerThread
import dagger.hilt.android.qualifiers.ApplicationContext
import org.elsysbg.synthi.data.model.Media
import org.elsysbg.synthi.data.model.Playlist
import javax.inject.Inject

@WorkerThread
class MediaRepositoryHelper @Inject constructor(@ApplicationContext val context: Context) {
    fun getMedias(): List<Media> {
        val medias = mutableListOf<Media>()

        val collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
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
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} = ?"
        val selectionArgs = arrayOf("1")
        val sortOrder = projection[0]
        val query = context.contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        query?.use { cursor ->
            while (cursor.moveToNext()) {
                cursor.apply {
                    val id = getLong(getColumnIndexOrThrow(projection[0]))
                    val displayName = getString(getColumnIndexOrThrow(projection[1]))
                    val duration = getLong(getColumnIndexOrThrow(projection[2]))
                    val title = getString(getColumnIndexOrThrow(projection[3]))
                    val artistId = getLong(getColumnIndexOrThrow(projection[4]))
                    val artist = getString(getColumnIndexOrThrow(projection[5]))
                    val albumId = getLong(getColumnIndexOrThrow(projection[6]))
                    val album = getString(getColumnIndexOrThrow(projection[7]))
                    val track = getInt(getColumnIndexOrThrow(projection[8]))
                    val contentUri = ContentUris.withAppendedId(collection, id)

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
        }
        return medias
    }

    @Suppress("DEPRECATION")
    fun getPlaylists(): List<Playlist> {
        val playlists = mutableListOf<Playlist>()

        val collection = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Audio.Playlists._ID, MediaStore.Audio.Playlists.NAME)
        val sortOrder = MediaStore.Audio.Playlists._ID
        val query = context.contentResolver.query(
            collection,
            projection,
            null,
            null,
            sortOrder
        )

        query?.use { cursor ->
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(projection[0]))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(projection[1]))

                playlists += Playlist(id, name, getPlaylistMembers(id))
            }
        }
        return playlists
    }

    @Suppress("DEPRECATION")
    private fun getPlaylistMembers(playlistId: Long): List<Media> {
        val members = mutableListOf<Media>()

        val collection = MediaStore.Audio.Playlists.Members.getContentUri("external", playlistId)
        val projection = arrayOf(
            MediaStore.Audio.Playlists.Members.AUDIO_ID,
            MediaStore.Audio.Playlists.Members.DISPLAY_NAME,
            MediaStore.Audio.Playlists.Members.DURATION,
            MediaStore.Audio.Playlists.Members.TITLE,
            MediaStore.Audio.Playlists.Members.ARTIST_ID,
            MediaStore.Audio.Playlists.Members.ARTIST,
            MediaStore.Audio.Playlists.Members.ALBUM_ID,
            MediaStore.Audio.Playlists.Members.ALBUM,
            MediaStore.Audio.Playlists.Members.TRACK,
            MediaStore.Audio.Playlists.Members.PLAY_ORDER
        )
        val selection = "${MediaStore.Audio.Playlists.Members.IS_MUSIC} = ?"
        val selectionArgs = arrayOf("1")
        val sortOrder = MediaStore.Audio.Playlists.Members.PLAY_ORDER
        val query = context.contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        query?.use { cursor ->
            while (cursor.moveToNext()) {
                cursor.apply {
                    val id = getLong(getColumnIndexOrThrow(projection[0]))
                    val displayName = getString(getColumnIndexOrThrow(projection[1]))
                    val duration = getLong(getColumnIndexOrThrow(projection[2]))
                    val title = getString(getColumnIndexOrThrow(projection[3]))
                    val artistId = getLong(getColumnIndexOrThrow(projection[4]))
                    val artist = getString(getColumnIndexOrThrow(projection[5]))
                    val albumId = getLong(getColumnIndexOrThrow(projection[6]))
                    val album = getString(getColumnIndexOrThrow(projection[7]))
                    val track = getInt(getColumnIndexOrThrow(projection[8]))
                    val playOrder = getInt(getColumnIndexOrThrow(projection[9]))
                    val contentUri = ContentUris.withAppendedId(MediaStore.Audio.Playlists.Members.getContentUri("external", playlistId),id)

                    members += Media(
                        contentUri,
                        id,
                        displayName,
                        duration,
                        title,
                        artistId,
                        artist,
                        albumId,
                        album,
                        track,
                        playOrder
                    )
                }
            }
        }
        return members
    }
}