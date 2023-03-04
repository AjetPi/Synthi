package org.elsysbg.synthi.ui.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.elsysbg.synthi.component.activity.MainViewModel

@Composable
fun SynthiApp(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SynthiTopBar(
                canNavigate = false,
                onBackClick = { navController.popBackStack() },
                search = uiState.search,
                onSearchClick = { viewModel.updateSearch(it) }
            )
        },
        bottomBar = {
            SynthiBottomBar(
                bottomNavigationState = uiState.bottomNavigationState,
                isSelected = { route -> currentDestination?.hierarchy?.any { it.route == route } == true },
                onCategoryClick = {
                    navController.navigate(it) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        SynthiNavHost(
            uiState = uiState,
            navController = navController,
            currentMedia = viewModel.currentMedia,
            playbackState = viewModel.playbackState.value,
            currentPosition = viewModel.currentPosition,
            seekTo = { viewModel.seekTo(it) },
            skipPrevious = { viewModel.skipPrevious() },
            skipNext = { viewModel.skipNext() },
            onPlay = { viewModel.playMedia(it) },
            onNavigate = { viewModel.updateBottomNavigationState(it) },
            modifier = modifier.padding(innerPadding)
        )
    }
}