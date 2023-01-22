package org.elsys.synthi.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import org.elsys.synthi.R
import org.elsys.synthi.data.LibraryType


@Composable
fun SynthiHomeScreen(
    synthiUiState: SynthiUiState,
    onTabPressed: (LibraryType) -> Unit,
    modifier: Modifier = Modifier
) {
    val navigationItemContentList = listOf(
        NavigationItemContent(
            libraryType = LibraryType.Songs,
            icon = Icons.Default.Favorite,
            text = stringResource(R.string.songs)
        ),
        NavigationItemContent(
            libraryType = LibraryType.Albums,
            icon = Icons.Default.Album,
            text = stringResource(R.string.albums)
        ),
        NavigationItemContent(
            libraryType = LibraryType.Artists,
            icon = Icons.Default.Favorite,
            text = stringResource(R.string.artists)
        ),
        NavigationItemContent(
            libraryType = LibraryType.Playlists,
            icon = Icons.Default.Favorite,
            text = stringResource(R.string.playlists)
        )
    )

    SynthiAppContent(
        synthiUiState = synthiUiState,
        onTabPressed = onTabPressed,
        navigationItemContentList = navigationItemContentList,
        modifier = modifier
    )
}

@Composable
private fun SynthiAppContent(
    synthiUiState: SynthiUiState,
    onTabPressed: (LibraryType) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
    ) {
        SynthiListOnlyContent(
            synthiUiState = synthiUiState,
            modifier = Modifier.weight(1f)
        )
        SynthiBottomNavigationBar(
            currentTab = synthiUiState.currentLibrary,
            onTabPressed = onTabPressed,
            navigationItemContentList = navigationItemContentList
        )
    }
}

@Composable
private fun SynthiBottomNavigationBar(
    currentTab: LibraryType,
    onTabPressed: (LibraryType) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier.fillMaxWidth()) {
        navigationItemContentList.forEach {
            NavigationBarItem(
                selected = currentTab == it.libraryType,
                onClick = { onTabPressed(it.libraryType) },
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = it.text)
                },
                alwaysShowLabel = false
            )
        }
    }
}

private data class NavigationItemContent(
    val libraryType: LibraryType,
    val icon: ImageVector,
    val text: String
)