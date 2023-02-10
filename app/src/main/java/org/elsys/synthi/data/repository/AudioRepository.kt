package org.elsys.synthi.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.elsys.synthi.data.AudioHelper
import org.elsys.synthi.data.model.Audio
import javax.inject.Inject

class AudioRepository @Inject constructor(private val audioHelper: AudioHelper) {
    suspend fun getAudioList(): List<Audio> = withContext(Dispatchers.IO) {
        audioHelper.getAudioList()
    }
}