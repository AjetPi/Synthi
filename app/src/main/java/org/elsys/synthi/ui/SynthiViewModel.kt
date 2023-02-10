package org.elsys.synthi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.elsys.synthi.data.model.Audio
import org.elsys.synthi.data.model.Category
import org.elsys.synthi.data.repository.AudioRepository
import javax.inject.Inject

@HiltViewModel
class SynthiViewModel @Inject constructor(val repository: AudioRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(SynthiUiState())
    val uiState: StateFlow<SynthiUiState> = _uiState

    init {
        _uiState.value = SynthiUiState()
        viewModelScope.launch {
            updateLibrary(repository.getAudioList())
        }
    }

    fun updateLibrary(library: List<Audio>) {
        _uiState.update {
            it.copy(
                library = library
            )
        }
    }

    fun updateCurrentCategory(category: Category) {
        _uiState.update {
            it.copy(
                currentCategory = category
            )
        }
    }
}