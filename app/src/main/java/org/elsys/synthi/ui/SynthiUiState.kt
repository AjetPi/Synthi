package org.elsys.synthi.ui

import org.elsys.synthi.data.model.Category
import org.elsys.synthi.data.model.Library

data class SynthiUiState(
    val library: Library = Library(),
    val currentCategory: Category = Category.Songs
)