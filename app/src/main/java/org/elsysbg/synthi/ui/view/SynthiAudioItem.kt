package org.elsysbg.synthi.ui.view

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.RequestManager
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@Composable
fun SynthiOrderedAudioItem(
    number: Int,
    title: String,
    subtitle: String?,
    modifier: Modifier = Modifier
) {
    SynthiAudioItem(title = title, subtitle = subtitle, modifier = modifier) {
        Text(text = number.toString(), modifier = Modifier.fillMaxSize(), style = MaterialTheme.typography.h4)
    }
}

@Composable
fun SynthiUnorderedAudioItem(
    requestManager: RequestManager,
    coverUri: Uri,
    imageVector: ImageVector,
    title: String,
    subtitle: String?,
    modifier: Modifier = Modifier
) {
    SynthiAudioItem(title = title, subtitle = subtitle, modifier = modifier) {
        SynthiCover(
            coverUri = coverUri,
            imageVector = imageVector,
            requestManager = requestManager,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun SynthiAudioItem(
    title: String,
    subtitle: String?,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Card(
            modifier = Modifier.requiredSize(52.dp),
            shape = MaterialTheme.shapes.small
        ) { content() }
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Column {
            Text(
                text = title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.subtitle1
            )
            if (subtitle != null)
                Text(
                    text = subtitle,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.subtitle2
                )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SynthiCover(
    coverUri: Uri,
    imageVector: ImageVector,
    requestManager: RequestManager,
    modifier: Modifier = Modifier
) {
    GlideImage(
        model = coverUri,
        contentDescription = null,
        modifier = modifier,
        failure = placeholder { Icon(imageVector = imageVector, contentDescription = null, modifier = modifier) }
    ) { it.thumbnail(requestManager.asDrawable().load(coverUri)) }
}