package org.elsysbg.synthi.ui

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.elsysbg.synthi.data.model.Library
import org.elsysbg.synthi.data.model.Media
import org.elsysbg.synthi.data.repository.MediaRepositoryImpl
import org.elsysbg.synthi.service.MediaServiceConnection
import org.elsysbg.synthi.util.Constants
import org.elsysbg.synthi.util.currentPosition
import org.elsysbg.synthi.util.isPlaying
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MediaRepositoryImpl,
    private val connection: MediaServiceConnection
) : ViewModel() {
    private val _uiState = MutableStateFlow(SynthiUiState())
    val uiState: StateFlow<SynthiUiState> = _uiState.asStateFlow()

    private val currentMediaMetadata = connection.currentMediaMetadata
    val currentMedia: Media?
        get() = uiState.value.library.songs.find { media -> media.id == currentMediaMetadata.value?.description?.mediaId?.toLong() }
    val playbackState = connection.playbackState
    val currentPosition: Long
        get() = playbackState.value?.currentPosition ?: 0

    init {
        updateLibrary()
        connection.subscribe(Constants.MEDIA_ROOT_ID, object : MediaBrowserCompat.SubscriptionCallback() {})
    }

    fun updateLibrary() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    library = Library(
                        songs = repository.getMedias(),
                        playlists = repository.getPlaylists()
                    )
                )
            }
        }
    }

    fun updateSearch(value: String?) = _uiState.update { it.copy(search = value) }

    fun updateBottomNavigationState(state: Boolean) = _uiState.update {
        it.copy(bottomNavigationState = state)
    }

    fun skipToNext() = connection.transportControls.skipToNext()

    fun skipToPrevious() = connection.transportControls.skipToPrevious()

    fun seekTo(pos: Long) = connection.transportControls.seekTo(pos)

    fun playFromMedia(media: Media) {
        connection.apply {
            if (media.id.toString() == currentMediaMetadata.value?.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)) {
                if (playbackState.value?.isPlaying == true) transportControls.pause() else transportControls.play()
            } else {
                transportControls.playFromMediaId(media.id.toString(), null)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        connection.unsubscribe(Constants.MEDIA_ROOT_ID, object : MediaBrowserCompat.SubscriptionCallback() {})
    }
}