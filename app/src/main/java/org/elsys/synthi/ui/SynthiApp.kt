package org.elsys.synthi.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import org.elsys.synthi.ui.theme.SynthiTheme

@Composable
fun SynthiApp(modifier: Modifier = Modifier) {
    val viewModel: SynthiViewModel = viewModel()
    val synthiUiState = viewModel.uiState.collectAsState().value

    SynthiHome(
        synthiUiState = synthiUiState,
        onCategoryClick = { viewModel.updateCurrentCategory(it) },
        modifier = modifier
    )
}

@Preview(showSystemUi = true)
@Composable
fun SynthiAppPreview() {
    SynthiTheme(darkTheme = true) {
        SynthiApp()
    }
}