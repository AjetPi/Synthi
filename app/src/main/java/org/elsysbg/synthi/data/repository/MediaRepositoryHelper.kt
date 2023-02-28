package org.elsysbg.synthi.data.repository

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.annotation.WorkerThread
import dagger.hilt.android.qualifiers.ApplicationContext
import org.elsysbg.synthi.data.model.Media
import org.elsysbg.synthi.data.model.Playlist
import javax.inject.Inject

@Suppress("DEPRECATION")
@WorkerThread
class MediaRepositoryHelper @Inject constructor(@ApplicationContext val context: Context) {
    private fun getMediaCursor(): Cursor? {
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
        val sortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER
        return context.contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )
    }

    private fun getMedia(cursor: Cursor): Media {
        cursor.apply {
            val id = getLong(getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
            val displayName = getString(getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
            val duration = getInt(getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
            val title = getString(getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
            val artistId = getLong(getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST_ID))
            val artist = getString(getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
            val albumId = getLong(getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID))
            val album = getString(getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM))
            val track = getInt(getColumnIndexOrThrow(MediaStore.Audio.Media.TRACK))
            val contentUri: Uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)

            return Media(
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

    fun getMedias(): List<Media> {
        val medias = mutableListOf<Media>()
        val cursor = getMediaCursor()

        cursor?.use { while (it.moveToNext()) medias += getMedia(it) }
        return medias
    }

    private fun getPlaylistCursor(): Cursor? {
        val collection = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Audio.Playlists._ID, MediaStore.Audio.Playlists.NAME)
        val sortOrder = MediaStore.Audio.Playlists.DEFAULT_SORT_ORDER

        return context.contentResolver.query(
            collection,
            projection,
            null,
            null,
            sortOrder
        )
    }

    fun getPlaylists(): List<Playlist> {
        val playlists = mutableListOf<Playlist>()
        val cursor = getPlaylistCursor()

        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Playlists._ID))
                val name = it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Playlists.NAME))

                playlists += Playlist(id, name, getPlaylistMembers(id))
            }
        }

        return playlists
    }

    private fun getPlaylistMemberCursor(playlistId: Long): Cursor? {
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
            MediaStore.Audio.Playlists.Members.TRACK
        )
        val selection = "${MediaStore.Audio.Playlists.Members.IS_MUSIC} = ?"
        val selectionArgs = arrayOf("1")
        val sortOrder = MediaStore.Audio.Playlists.Members.DEFAULT_SORT_ORDER
        return context.contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )
    }

    private fun getPlaylistMember(cursor: Cursor, playlistId: Long): Media {
        cursor.apply {
            val id = getLong(getColumnIndexOrThrow(MediaStore.Audio.Playlists.Members.AUDIO_ID))
            val displayName = getString(getColumnIndexOrThrow(MediaStore.Audio.Playlists.Members.DISPLAY_NAME))
            val duration = getInt(getColumnIndexOrThrow(MediaStore.Audio.Playlists.Members.DURATION))
            val title = getString(getColumnIndexOrThrow(MediaStore.Audio.Playlists.Members.TITLE))
            val artistId = getLong(getColumnIndexOrThrow(MediaStore.Audio.Playlists.Members.ARTIST_ID))
            val artist = getString(getColumnIndexOrThrow(MediaStore.Audio.Playlists.Members.ARTIST))
            val albumId = getLong(getColumnIndexOrThrow(MediaStore.Audio.Playlists.Members.ALBUM_ID))
            val album = getString(getColumnIndexOrThrow(MediaStore.Audio.Playlists.Members.ALBUM))
            val track = getInt(getColumnIndexOrThrow(MediaStore.Audio.Playlists.Members.TRACK))
            val contentUri: Uri = ContentUris.withAppendedId(MediaStore.Audio.Playlists.Members.getContentUri("external", playlistId), id)

            return Media(
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

    private fun getPlaylistMembers(playlistId: Long): List<Media> {
        val members = mutableListOf<Media>()
        val cursor = getPlaylistMemberCursor(playlistId)

        cursor?.use { while (it.moveToNext()) members += getPlaylistMember(it, playlistId) }
        return members
    }
}