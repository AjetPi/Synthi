package org.elsysbg.synthi.ui.view

import android.support.v4.media.session.PlaybackStateCompat
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.RequestManager
import org.elsysbg.synthi.data.model.Category
import org.elsysbg.synthi.data.model.Media
import org.elsysbg.synthi.domain.util.isPlaying

@Composable
fun SynthiPlayerScreen(
    media: Media,
    position: Long,
    playbackState: PlaybackStateCompat,
    seekTo: (Long) -> Unit,
    skipPrevious: () -> Unit,
    play: (Media) -> Unit,
    skipNext: () -> Unit,
    modifier: Modifier = Modifier,
    requestManager: RequestManager,
) {
    Column {
        SynthiCover(
            coverUri = media.coverUri,
            imageVector = Category.Albums.icon,
            requestManager = requestManager,
            modifier = Modifier
                .requiredSize(260.dp)
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.requiredHeight(56.dp))
        Text(text = media.title, style = MaterialTheme.typography.subtitle1)
        Text(text = media.artist, style = MaterialTheme.typography.subtitle2, modifier = Modifier.padding(bottom = 8.dp))
        Slider(value = position.toFloat() / media.duration, onValueChange = {}, onValueChangeFinished = {})
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(text = "${position / 1000 / 60}:${position / 1000 % 60}", modifier = Modifier.align(Alignment.CenterStart))
            Text(text = "${media.duration / 1000 / 60}:${media.duration / 1000 % 60}", modifier = Modifier.align(Alignment.CenterEnd))
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            IconButton(onClick = skipPrevious) {
                Icon(imageVector = Icons.Default.SkipPrevious, contentDescription = null)
            }
            IconButton(onClick = { play(media) }) {
                Icon(imageVector = if (playbackState.isPlaying) Icons.Default.PauseCircle else Icons.Default.PlayCircle, contentDescription = null, modifier = Modifier.padding(horizontal = 12.dp))
            }
            IconButton(onClick = skipNext) {
                Icon(imageVector = Icons.Default.SkipNext, contentDescription = null)
            }
        }
    }
}