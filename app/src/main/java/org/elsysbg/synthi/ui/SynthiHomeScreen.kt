package org.elsysbg.synthi.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import org.elsysbg.synthi.R
import org.elsysbg.synthi.data.model.Category
import org.elsysbg.synthi.data.model.Media

@Composable
fun SynthiHomeScreen(
    uiState: SynthiUiState,
    onBackClick: () -> Unit,
    onSearchClick: (String?) -> Unit,
    onCategoryClick: (Category) -> Unit,
    onItemClick: (Media) -> Unit,
    onListClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SynthiTopBar(
                back = uiState.selectedListId != null,
                onBackClick = onBackClick,
                search = uiState.search,
                onSearchClick = onSearchClick
            )
        },
        bottomBar = {
            SynthiBottomBar(
                media = uiState.currentMedia,
                onPlayClick = onItemClick,
                selected = uiState.currentCategory,
                onCategoryClick = onCategoryClick
            )
        }
    ) { innerPadding ->
        SynthiCategoryContent(
            uiState = uiState,
            onItemClick = onItemClick,
            onListClick = onListClick,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun SynthiTopBar(
    back: Boolean,
    onBackClick: () -> Unit,
    search: String?,
    onSearchClick: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar (
        title = {
            if (search == null) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.h6
                )
            }
            else {
                TextField(
                    value = search,
                    onValueChange = { onSearchClick(it) },
                    placeholder = {
                        Text(
                            modifier = Modifier.alpha(ContentAlpha.medium),
                            text = stringResource(id = R.string.search)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onSearchClick(null)
                        }
                    )
                )
            }
        },
        modifier = modifier,
        navigationIcon = {
            if (back) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = { onSearchClick(if (search == null) "" else null) }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search)
                )
            }
        }
    )
}

@Composable
private fun SynthiBottomBar(
    media: Media?,
    onPlayClick: (Media) -> Unit,
    selected: Category,
    onCategoryClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        if (media != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
            ) {
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    color = MaterialTheme.colors.onPrimary,
                    text = media.title,
                    style = MaterialTheme.typography.subtitle1
                )
                IconButton(
                    modifier = Modifier.padding(end = 12.dp),
                    onClick = { onPlayClick(media) }
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                    )
                }
            }
        }
        BottomNavigation(modifier = modifier.fillMaxWidth()) {
            Category.values().forEach { category ->
                BottomNavigationItem(
                    selected = selected == category,
                    onClick = { onCategoryClick(category) },
                    icon = {
                        Icon(
                            imageVector = category.icon,
                            contentDescription = category.name
                        )
                    },
                    label = { Text(text = stringResource(id = category.id)) },
                    alwaysShowLabel = false
                )
            }
        }
    }
}

@Composable
fun SynthiCategoryContent(
    uiState: SynthiUiState,
    onItemClick: (Media) -> Unit,
    onListClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    uiState.apply {
        if (library.songs.isNotEmpty()) {
            when (currentCategory) {
                Category.Songs -> {
                    val medias = library.songs.filter { it.title.contains(search ?: "", ignoreCase = true) }
                    LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {
                        items(medias) { media ->
                            SynthiCategoryItem(
                                category = currentCategory,
                                title = media.title,
                                subtitle = media.artist,
                                onItemClick = { onItemClick(media) }
                            )
                        }
                    }
                }
                Category.Albums -> {
                    val albums = library.songs.filter { it.album.contains(search ?: "", ignoreCase = true) }.groupBy { it.albumId }
                    if (selectedListId == null) {
                        LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {
                            items(albums.keys.toList()) { id ->
                                val media = albums[id]!!.first()
                                SynthiCategoryItem(
                                    category = currentCategory,
                                    title = media.album,
                                    subtitle = media.artist,
                                    onItemClick = { onListClick(id) }
                                )
                            }
                        }
                    }
                    else {
                        LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {
                            items(library.albums[selectedListId]!!) { media ->
                                SynthiCategoryItem(
                                    category = Category.Songs,
                                    title = media.title,
                                    subtitle = "",
                                    onItemClick = { onItemClick(media) }
                                )
                            }
                        }
                    }
                }
                Category.Artists -> {
                    val artists = library.songs.filter { it.artist.contains(search ?: "", ignoreCase = true) }.groupBy { it.artistId }
                    if (selectedListId == null) {
                        LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {
                            items(artists.keys.toList()) { id ->
                                val media = artists[id]!!.first()
                                SynthiCategoryItem(
                                    category = currentCategory,
                                    title = media.artist,
                                    subtitle = "",
                                    onItemClick = { onListClick(id) }
                                )
                            }
                        }
                    }
                    else {
                        LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {
                            items(library.artists[selectedListId]!!) { media ->
                                SynthiCategoryItem(
                                    category = Category.Songs,
                                    title = media.title,
                                    subtitle = "",
                                    onItemClick = { onItemClick(media) }
                                )
                            }
                        }
                    }
                }
                Category.Playlists -> {}
            }
        }
    }
}

@Composable
fun SynthiCategoryItem(
    category: Category,
    title: String,
    subtitle: String,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable {
                onItemClick()
            }
    ) {
        Row(
            modifier = Modifier.padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp)
            )
            Column {
                Text(
                    text = title,
                    modifier = Modifier.padding(bottom = 2.dp),
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    }
}