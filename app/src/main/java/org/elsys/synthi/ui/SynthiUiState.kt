package org.elsys.synthi.ui

import org.elsys.synthi.data.LibraryType
import org.elsys.synthi.data.Song

data class SynthiUiState(
    val songs: List<Song> = emptyList(),
    val currentLibrary: LibraryType = LibraryType.Songs
)