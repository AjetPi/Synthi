package org.elsysbg.synthi.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.elsysbg.synthi.R
import org.elsysbg.synthi.data.model.Category
import org.elsysbg.synthi.data.model.Media

@Composable
fun SynthiHomeScreen(
    synthiUiState: SynthiUiState,
    onCategoryClick: (Category) -> Unit,
    modifier: Modifier = Modifier,
    onItemClick: (Media) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { SynthiTopAppBar() },
        bottomBar = {
            SynthiBottomNavigation(
                selected = synthiUiState.currentCategory,
                onClick = onCategoryClick
            )
        }
    ) { padding ->
        SynthiCategory(
            synthiUiState = synthiUiState,
            modifier = modifier.padding(padding),
            onItemClick
        )
    }
}

@Composable
private fun SynthiTopAppBar(modifier: Modifier = Modifier) {
    TopAppBar (
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h6
            )
        },
        modifier = modifier,
        navigationIcon = {
            Icon(
                modifier = Modifier.padding(start = 16.dp),
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        /*actions = {
            Icon(
                modifier = Modifier.padding(end = 16.dp),
                imageVector = Icons.Default.Settings,
                contentDescription = null
            )
        }*/
    )
}

@Composable
private fun SynthiBottomNavigation(
    selected: Category,
    onClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomNavigation(modifier = modifier.fillMaxWidth()) {
        Category.values().forEach { category ->
            BottomNavigationItem(
                selected = selected == category,
                onClick = { onClick(category) },
                icon = { Icon(imageVector = category.icon, contentDescription = category.name) },
                label = { Text(text = stringResource(id = category.id)) },
                alwaysShowLabel = false
            )
        }
    }
}