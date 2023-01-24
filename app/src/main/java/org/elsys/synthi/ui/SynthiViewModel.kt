package org.elsys.synthi.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.elsys.synthi.data.Audio
import org.elsys.synthi.data.Category

class SynthiViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SynthiUiState())
    val uiState: StateFlow<SynthiUiState> = _uiState

    init {
        _uiState.value = SynthiUiState()
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