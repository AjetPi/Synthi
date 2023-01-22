package org.elsys.synthi.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SynthiApp(modifier: Modifier = Modifier) {
    val viewModel: SynthiViewModel = viewModel()
    val synthiUiState = viewModel.uiState.collectAsState().value

    SynthiHomeScreen(
        synthiUiState = synthiUiState,
        onTabPressed = {
            viewModel.updateCurrentLibrary(it)
        },
        modifier = modifier
    )
}