package org.elsys.synthi.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.elsys.synthi.R
import org.elsys.synthi.data.model.Category

@Composable
fun SynthiHome(
    synthiUiState: SynthiUiState,
    onCategoryClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { SynthiTopBar() },
        bottomBar = {
            SynthiBottomBar(
                selected = synthiUiState.currentCategory,
                onClick = onCategoryClick
            )
        }
    ) { padding ->
        SynthiCategory(
            synthiUiState = synthiUiState,
            modifier = modifier.padding(padding)
        )
    }
}

@Composable
private fun SynthiTopBar(modifier: Modifier = Modifier) {
    TopAppBar (
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.subtitle1
            )
        },
        modifier = modifier,
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        actions = {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null
            )
        }
    )
}

@Composable
private fun SynthiBottomBar(
    selected: Category,
    onClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomNavigation(modifier = modifier.fillMaxWidth()) {
        Category.values().forEach {
            BottomNavigationItem(
                selected = selected == it,
                onClick = { onClick(it) },
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = stringResource(id = it.id))
                },
                alwaysShowLabel = false
            )
        }
    }
}