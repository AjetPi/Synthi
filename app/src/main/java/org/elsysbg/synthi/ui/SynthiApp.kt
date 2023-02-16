package org.elsysbg.synthi.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import org.elsysbg.synthi.ui.theme.SynthiTheme

@Composable
fun SynthiApp(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    val synthiUiState = viewModel.uiState.collectAsState().value

    SynthiHomeScreen(
        synthiUiState = synthiUiState,
        onCategoryClick = { viewModel.updateCurrentCategory(it) },
        modifier = modifier,
        onItemClick = { viewModel.playFromMedia(it) }
    )
}

@Preview(showSystemUi = true)
@Composable
fun SynthiAppPreview() {
    SynthiTheme {
        SynthiApp()
    }
}