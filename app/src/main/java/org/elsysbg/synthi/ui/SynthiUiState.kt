package org.elsysbg.synthi.ui

import org.elsysbg.synthi.data.model.Library

data class SynthiUiState(
    val library: Library = Library(),
    val search: String? = null,
    val topAppBarState: Boolean = true,
    val topSearchState: Boolean = false,
    val bottomPlayerState: Boolean = false,
    val bottomNavigationState: Boolean = true
)