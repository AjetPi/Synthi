package org.elsys.synthi.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.elsys.synthi.data.LibraryType
import org.elsys.synthi.data.Song

class SynthiViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SynthiUiState())
    val uiState: StateFlow<SynthiUiState> = _uiState

    init {
        initializeUiState()
    }

    private fun initializeUiState() {
        val songs = listOf(
            Song(1, "Plainsong", "Disintegration", "The Cure"),
            Song(2, "Pictures Of You", "Disintegration", "The Cure"),
            Song(3, "Closedown", "Disintegration", "The Cure"),
            Song(4, "Lovesong", "Disintegration", "The Cure"),
            Song(5, "Last Dance", "Disintegration", "The Cure"),
            Song(6, "Lullaby", "Disintegration", "The Cure"),
            Song(7, "Fascination Street", "Disintegration", "The Cure"),
            Song(8, "Prayers For Rain", "Disintegration", "The Cure"),
            Song(9, "The Same Deep Water As You", "Disintegration", "The Cure"),
            Song(10, "Disintegration", "Disintegration", "The Cure"),
            Song(11, "Homesick", "Disintegration", "The Cure"),
            Song(12, "Untitled", "Disintegration", "The Cure")
        )

        _uiState.value = SynthiUiState(songs = songs)
    }

    fun updateSongs(songs: List<Song>) {
        _uiState.update {
            it.copy(
                songs = songs
            )
        }
    }

    fun updateCurrentLibrary(libraryType: LibraryType) {
        _uiState.update {
            it.copy(
                currentLibrary = libraryType
            )
        }
    }
}