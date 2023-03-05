package org.elsysbg.synthi.component.activity

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.elsysbg.synthi.data.model.Library
import org.elsysbg.synthi.data.model.Media
import org.elsysbg.synthi.data.repository.MediaRepositoryImpl
import org.elsysbg.synthi.component.service.MediaServiceConnection
import org.elsysbg.synthi.domain.util.Constants
import org.elsysbg.synthi.domain.util.currentPosition
import org.elsysbg.synthi.domain.util.isPlaying
import org.elsysbg.synthi.ui.SynthiUiState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MediaRepositoryImpl,
    private val connection: MediaServiceConnection,
    val requestManager: RequestManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(SynthiUiState())
    val uiState: StateFlow<SynthiUiState> = _uiState.asStateFlow()

    val playbackState = connection.playbackState
    private val currentMetadata = connection.currentMetadata
    val currentMedia: Media?
        get() = uiState.value.library.songs.find { media -> media.id == currentMetadata.value?.description?.mediaId?.toLong() }
    val currentPosition = mutableStateOf(0L)

    init {
        updateLibrary()
        updatePosition()
        connection.subscribe(Constants.MEDIA_ROOT_ID, object : MediaBrowserCompat.SubscriptionCallback() {})
    }

    fun updateLibrary() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    library = Library(
                        songs = repository.getMedias(),
                        albums = repository.getAlbums(),
                        artists = repository.getArtists(),
                        playlists = repository.getPlaylists()
                    )
                )
            }
        }
    }

    private fun updatePosition() {
        viewModelScope.launch {
            while (this.isActive) {
                currentPosition.value = playbackState.value?.currentPosition ?: 0
                delay(100L)
            }
        }
    }

    fun updateSearch(value: String?) = _uiState.update { it.copy(search = value) }

    fun updateBottomNavigationState(state: Boolean) = _uiState.update { it.copy(bottomNavigationState = state) }

    fun skipNext() = connection.controls.skipToNext()

    fun skipPrevious() = connection.controls.skipToPrevious()

    fun seekTo(pos: Long) = connection.controls.seekTo(pos)

    fun playMedia(media: Media) {
        connection.apply {
            if (media.id.toString() == currentMetadata.value?.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)) {
                if (playbackState.value?.isPlaying == true) controls.pause() else controls.play()
            } else {
                controls.playFromMediaId(media.id.toString(), null)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        connection.unsubscribe(Constants.MEDIA_ROOT_ID, object : MediaBrowserCompat.SubscriptionCallback() {})
    }
}