package org.elsysbg.synthi.ui.view

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.bumptech.glide.RequestManager
import org.elsysbg.synthi.data.model.Media
import org.elsysbg.synthi.ui.Category

@Composable
fun SynthiDetailsContent(
    list: List<Media>,
    icon: ImageVector,
    title: String,
    onItemClick: (Media) -> Unit,
    modifier: Modifier = Modifier,
    requestManager: RequestManager,
    coverUri: Uri
) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {
        item {
            Column {
                SynthiCover(
                    coverUri = coverUri,
                    imageVector = icon,
                    requestManager = requestManager,
                    modifier = Modifier
                        .requiredSize(208.dp)
                        .padding(vertical = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(text = title, style = MaterialTheme.typography.h5, modifier = Modifier.align(Alignment.CenterHorizontally))
                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp))
            }
        }
        items(list) { media ->
            SynthiUnorderedAudioItem(
                requestManager = requestManager,
                coverUri = media.coverUri,
                imageVector = Category.Songs.icon,
                title = media.title,
                subtitle = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onItemClick(media) }
            )
        }
    }
}