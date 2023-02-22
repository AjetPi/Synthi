package org.elsysbg.synthi.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.elsysbg.synthi.data.model.Media
import javax.inject.Inject

interface MediaRepository {
    suspend fun getMedias(): List<Media>
}

class DefaultMediaRepository @Inject constructor(private val mediaHelper: MediaHelper): MediaRepository {
    private lateinit var medias: List<Media>

    override suspend fun getMedias() = withContext(Dispatchers.IO) {
        if (!::medias.isInitialized){
            medias = mediaHelper.getMedias()
        }
        medias
    }
}