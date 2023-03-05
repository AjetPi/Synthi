package org.elsysbg.synthi.component.service.player

import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.session.PlaybackStateCompat
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import org.elsysbg.synthi.component.service.MediaService

class PlaybackPreparerImpl(private val service: MediaService) : MediaSessionConnector.PlaybackPreparer {
    override fun onCommand(player: Player, command: String, extras: Bundle?, cb: ResultReceiver?) = false

    override fun getSupportedPrepareActions() = PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID or PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID

    override fun onPrepare(playWhenReady: Boolean) = Unit

    override fun onPrepareFromMediaId(mediaId: String, playWhenReady: Boolean, extras: Bundle?) {
        service.apply {
            mediaSource.whenReady {
                val itemToPlay = mediaSource.metadataList.find { it.description.mediaId == mediaId }
                currentMetadata = itemToPlay
                prepare(mediaSource.metadataList, itemToPlay, playWhenReady)
            }
        }
    }

    override fun onPrepareFromSearch(query: String, playWhenReady: Boolean, extras: Bundle?) = Unit

    override fun onPrepareFromUri(uri: Uri, playWhenReady: Boolean, extras: Bundle?) = Unit
}