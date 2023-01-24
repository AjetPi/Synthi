package org.elsys.synthi.ui

import org.elsys.synthi.data.Audio
import org.elsys.synthi.data.Category

data class SynthiUiState(
    val library: List<Audio> = emptyList(),
    val currentCategory: Category = Category.Songs
)