package org.elsysbg.synthi.service

import android.support.v4.media.MediaDescriptionCompat
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator

class TimelineQueueNavigatorImpl(private val service: MediaService) : TimelineQueueNavigator(service.mediaSession) {
    override fun getMediaDescription(
        player: Player,
        windowIndex: Int
    ): MediaDescriptionCompat {
        service.apply {
            if (windowIndex < mediaSource.mediasMetadata.size) {
                return mediaSource.mediasMetadata[windowIndex].description
            }
        }

        return MediaDescriptionCompat.Builder().build()
    }
}