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

class MediaRepositoryImpl @Inject constructor(private val helper: MediaRepositoryHelper): MediaRepository {
    private lateinit var medias: List<Media>
    private lateinit var playlists: List<Playlist>

    override suspend fun getMedias() = withContext(Dispatchers.IO) {
        if (!::medias.isInitialized){
            medias = helper.getMedias()
        }
        medias
    }

    override suspend fun getPlaylists() = withContext(Dispatchers.IO) {
        if (!::playlists.isInitialized){
            playlists = helper.getPlaylists()
        }
        playlists
    }
}