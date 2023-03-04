package org.elsysbg.synthi.ui.view

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.elsysbg.synthi.data.model.Category
import org.elsysbg.synthi.data.model.Media
import org.elsysbg.synthi.ui.SynthiUiState

@Composable
fun SynthiHomeScreen(
    uiState: SynthiUiState,
    currentCategory: Category,
    onItemClick: (Media) -> Unit,
    onListClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    SynthiHomeContent(
        uiState = uiState,
        category = currentCategory,
        onItemClick = onItemClick,
        onListClick = onListClick,
        modifier = modifier
    )
}