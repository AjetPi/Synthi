package org.elsysbg.synthi.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import org.elsysbg.synthi.ui.theme.SynthiTheme

@Composable
fun SynthiApp(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    SynthiHomeScreen(
        uiState = uiState,
        onBackClick = { viewModel.selectList(null) },
        onSearchClick = { viewModel.updateSearch(it) },
        onCategoryClick = { viewModel.updateCurrentCategory(it) },
        onItemClick = { viewModel.playFromMedia(it) },
        onListClick = { viewModel.selectList(it) },
        modifier = modifier
    )
}

@Preview(showSystemUi = true)
@Composable
fun SynthiAppPreview() {
    SynthiTheme {
        SynthiApp()
    }
}