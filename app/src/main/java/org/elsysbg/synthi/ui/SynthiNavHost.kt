package org.elsysbg.synthi.ui

import android.support.v4.media.session.PlaybackStateCompat
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
    currentMedia: Media?,
    playbackState: PlaybackStateCompat?,
    currentPosition: Long,
    seekTo: (Long) -> Unit,
    skipPrevious: () -> Unit,
    skipNext: () -> Unit,
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
                        onItemClick = {
                            onPlay(it)
                            navController.navigate("Player")
                        },
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
                        onItemClick = {
                            onPlay(it)
                            navController.navigate("Player")
                        }
                    )
                }
            }
        }

        composable(route = "Player") {
            if (currentMedia != null && playbackState != null) {
                SynthiPlayerScreen(
                    media = currentMedia,
                    playbackState = playbackState,
                    position = currentPosition,
                    seekTo = seekTo,
                    skipPrevious = skipPrevious,
                    play = onPlay,
                    skipNext = skipNext
                )
            }
        }
    }
}