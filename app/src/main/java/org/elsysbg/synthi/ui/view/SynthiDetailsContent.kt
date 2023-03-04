package org.elsysbg.synthi.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.elsysbg.synthi.data.model.Category
import org.elsysbg.synthi.data.model.Media

@Composable
fun SynthiDetailsContent(
    list: List<Media>,
    category: Category,
    title: String,
    onItemClick: (Media) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {
        item {
            Column {
                Icon(
                    imageVector = category.icon,
                    contentDescription = null,
                    modifier = Modifier
                        .requiredSize(192.dp)
                        .padding(vertical = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(text = title, style = MaterialTheme.typography.h5, modifier = Modifier.align(Alignment.CenterHorizontally))
                Divider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
            }
        }
        items(list) { media ->
            SynthiAudioItem(
                category = Category.Songs,
                title = media.title,
                subtitle = "",
                onItemClick = { onItemClick(media) }
            )
        }
    }
}