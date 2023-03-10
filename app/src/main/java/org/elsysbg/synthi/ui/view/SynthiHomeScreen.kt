package org.elsysbg.synthi.ui.view

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.bumptech.glide.RequestManager
import org.elsysbg.synthi.ui.Category
import org.elsysbg.synthi.data.model.Media
import org.elsysbg.synthi.ui.SynthiUiState

@Composable
fun SynthiHomeScreen(
    uiState: SynthiUiState,
    currentCategory: Category,
    onItemClick: (Media) -> Unit,
    onListClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    requestManager: RequestManager
) {
    SynthiHomeContent(
        library = uiState.library,
        search = uiState.search,
        category = currentCategory,
        onItemClick = onItemClick,
        onListClick = onListClick,
        modifier = modifier,
        requestManager = requestManager
    )
}