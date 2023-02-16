package org.elsysbg.synthi.ui

import org.elsysbg.synthi.data.model.Category
import org.elsysbg.synthi.data.model.Library

data class SynthiUiState(
    val library: Library = Library(),
    val currentCategory: Category = Category.Songs
)