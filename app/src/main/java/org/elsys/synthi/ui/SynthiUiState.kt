package org.elsys.synthi.ui

import org.elsys.synthi.data.model.Audio
import org.elsys.synthi.data.model.Category

data class SynthiUiState(
    val library: List<Audio> = emptyList(),
    val currentCategory: Category = Category.Songs
)