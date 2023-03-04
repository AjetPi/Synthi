package org.elsysbg.synthi.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.elsysbg.synthi.data.model.Album
import org.elsysbg.synthi.data.model.Artist
import org.elsysbg.synthi.data.model.Media
import org.elsysbg.synthi.data.model.Playlist
import javax.inject.Inject

interface MediaRepository {
    suspend fun getMedias(): List<Media>
    suspend fun getAlbums(): List<Album>
    suspend fun getArtists(): List<Artist>
    suspend fun getPlaylists(): List<Playlist>
}

class MediaRepositoryImpl @Inject constructor(private val helper: MediaRepositoryHelper): MediaRepository {
    private lateinit var medias: List<Media>
    private lateinit var albums: List<Album>
    private lateinit var artists: List<Artist>
    private lateinit var playlists: List<Playlist>

    override suspend fun getMedias() = withContext(Dispatchers.IO) {
        if (!::medias.isInitialized) {
            medias = helper.getMedias()
            albums = medias.groupBy { it.albumId }.map { Album(it.value) }
            artists = albums.groupBy { it.artistId }.map { Artist(it.value) }
        }
        medias
    }

    override suspend fun getAlbums() = withContext(Dispatchers.IO) {
        if (!::medias.isInitialized) {
            getMedias()
        }
        albums
    }

    override suspend fun getArtists() = withContext(Dispatchers.IO) {
        if (!::medias.isInitialized) {
            getMedias()
        }
        artists
    }

    override suspend fun getPlaylists() = withContext(Dispatchers.IO) {
        if (!::playlists.isInitialized) {
            playlists = helper.getPlaylists()
        }
        playlists
    }
}