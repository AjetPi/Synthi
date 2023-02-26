package org.elsysbg.synthi.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.elsysbg.synthi.data.model.Media
import org.elsysbg.synthi.data.model.Playlist
import javax.inject.Inject

interface MediaRepository {
    suspend fun getMedias(): List<Media>
    suspend fun getPlaylists(): List<Playlist>
}

class DefaultMediaRepository @Inject constructor(private val mediaHelper: MediaHelper): MediaRepository {
    private lateinit var medias: List<Media>
    private lateinit var playlists: List<Playlist>

    override suspend fun getMedias() = withContext(Dispatchers.IO) {
        if (!::medias.isInitialized){
            medias = mediaHelper.getMedias()
        }
        medias
    }

    override suspend fun getPlaylists() = withContext(Dispatchers.IO) {
        if (!::playlists.isInitialized){
            playlists = mediaHelper.getPlaylists()
        }
        playlists
    }
}