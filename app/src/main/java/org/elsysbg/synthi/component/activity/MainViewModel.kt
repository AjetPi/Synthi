package org.elsysbg.synthi.component.activity

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
                        albums = repository.getAlbums(),
                        artists = repository.getArtists(),
                        playlists = repository.getPlaylists()
                    )
                )
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
            if (media.id.toString() == currentMediaMetadata.value?.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)) {
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