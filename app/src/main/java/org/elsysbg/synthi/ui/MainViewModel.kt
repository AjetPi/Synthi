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
import org.elsysbg.synthi.data.model.Category
import org.elsysbg.synthi.data.model.Library
import org.elsysbg.synthi.data.model.Media
import org.elsysbg.synthi.data.repository.DefaultMediaRepository
import org.elsysbg.synthi.service.MediaServiceConnection
import org.elsysbg.synthi.util.Constants
import org.elsysbg.synthi.util.isPlaying
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DefaultMediaRepository,
    private val serviceConnection: MediaServiceConnection
) : ViewModel() {
    private val _uiState = MutableStateFlow(SynthiUiState())
    val uiState: StateFlow<SynthiUiState> = _uiState.asStateFlow()

    init {
        setLibrary()
        serviceConnection.subscribe(Constants.MEDIA_ROOT_ID, object : MediaBrowserCompat.SubscriptionCallback() {})
    }

    fun setLibrary() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    library = Library(songs = repository.getMedias())
                )
            }
        }
    }

    fun updateCurrentCategory(category: Category) {
        _uiState.update {
            it.copy(
                currentCategory = category
            )
        }
    }

    fun skipToNext() {
        serviceConnection.transportControls.skipToNext()
    }

    fun skipToPrevious() {
        serviceConnection.transportControls.skipToPrevious()
    }

    fun seekTo(pos: Long) {
        serviceConnection.transportControls.seekTo(pos)
    }

    fun playFromMedia(media: Media) {
        serviceConnection.apply {
            if (media.id.toString() == currentMediaMetadata.value?.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)) {
                if (playbackState.value?.isPlaying == true) transportControls.pause() else transportControls.play()
            } else {
                transportControls.playFromMediaId(media.id.toString(), null)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        serviceConnection.unsubscribe(Constants.MEDIA_ROOT_ID, object : MediaBrowserCompat.SubscriptionCallback() {})
    }
}