package org.elsysbg.synthi.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import org.elsysbg.synthi.data.model.Category
import org.elsysbg.synthi.data.model.Media

@Composable
fun SynthiNavHost(
    navController: NavHostController,
    uiState: SynthiUiState,
    onPlay: (Media) -> Unit,
    onNavigate: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = "Library",
        modifier = modifier.padding()
    ) {
        navigation(startDestination = Category.Songs.name, route = "Library") {
            Category.values().forEach { category ->
                composable(category.name) {
                    SynthiHomeScreen(
                        uiState = uiState,
                        currentCategory = category,
                        onItemClick = onPlay,
                        onListClick = { listId ->
                            onNavigate(false)
                            navController.navigate("${category.name}/$listId")
                        }
                    )
                }
                composable(
                    "${category.name}/{listId}",
                    arguments = listOf(navArgument("listId") { type = NavType.LongType })
                ) { backStackEntry ->
                    BackHandler {
                        onNavigate(true)
                        navController.navigateUp()
                    }
                    SynthiDetailsScreen(
                        library = uiState.library,
                        category = category,
                        listId = backStackEntry.arguments!!.getLong("listId"),
                        onItemClick = onPlay
                    )
                }
            }
        }
    }
}