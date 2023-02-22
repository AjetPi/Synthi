package org.elsysbg.synthi.ui

import org.elsysbg.synthi.data.model.Category
import org.elsysbg.synthi.data.model.Library
import org.elsysbg.synthi.data.model.Media

data class SynthiUiState(
    val library: Library = Library(),
    val currentCategory: Category = Category.Songs,
    val currentMedia: Media? = null,
    val selectedListId: Long? = null,
    val search: String? = null
)