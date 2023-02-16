package org.elsysbg.synthi.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.elsysbg.synthi.data.model.Category
import org.elsysbg.synthi.data.model.Media

@Composable
fun SynthiCategory(
    synthiUiState: SynthiUiState,
    modifier: Modifier = Modifier,
    onItemClick: (Media) -> Unit
) {
    synthiUiState.apply {
        if (library.songs.isNotEmpty()) {
            when (currentCategory) {
                Category.Songs -> {
                    LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {
                        items(library.songs) { media ->
                            SynthiMediaItem(
                                category = currentCategory,
                                media = media,
                                onItemClick = { onItemClick(media) })
                        }
                    }
                }
                Category.Albums -> {
                    LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {
                        items(library.albums.keys.toList()) {
                            SynthiMediaList(
                                category = currentCategory,
                                list = library.albums[it]!!,
                                onItemClick = onItemClick
                            )
                        }
                    }
                }
                Category.Artists -> {
                    LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {
                        items(synthiUiState.library.artists.keys.toList()) {
                            SynthiMediaList(
                                category = currentCategory,
                                list = library.artists[it]!!,
                                onItemClick = onItemClick
                            )
                        }
                    }
                }
                Category.Playlists -> {}
            }
        }
    }
}

@Composable
fun SynthiMediaItem(
    category: Category,
    media: Media,
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
                    text = media.title,
                    modifier = Modifier.padding(bottom = 2.dp),
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = media.artist,
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    }
}

@Composable
fun SynthiMediaList(
    category: Category,
    list: List<Media>,
    onItemClick: (Media) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expandedState by remember {
        mutableStateOf(false)
    }

    Column {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .clickable { expandedState = !expandedState }
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
                        text = if (category == Category.Albums) list[0].album else list[0].artist,
                        modifier = Modifier.padding(bottom = 2.dp),
                        style = MaterialTheme.typography.subtitle1
                    )
                    Text(
                        text = "${list.size} songs",
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }
        }
        if (expandedState) {
            Column {
                list.forEach {
                    SynthiMediaItem(
                        category = Category.Songs,
                        media = it,
                        onItemClick = { onItemClick(it) })
                }
            }
        }
    }
}