package org.elsys.synthi.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import org.elsys.synthi.data.Audio
import org.elsys.synthi.ui.theme.SynthiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SynthiApp(
    library: List<Audio>,
    modifier: Modifier = Modifier,
) {
    val viewModel: SynthiViewModel = viewModel()
    viewModel.updateLibrary(library)
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
        SynthiApp(emptyList())
    }
}