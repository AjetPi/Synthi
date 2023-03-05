package org.elsysbg.synthi.component.service.player

import android.support.v4.media.MediaDescriptionCompat
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
import org.elsysbg.synthi.component.service.MediaService

class TimelineQueueNavigatorImpl(private val service: MediaService) : TimelineQueueNavigator(service.session) {
    override fun getMediaDescription(player: Player, windowIndex: Int): MediaDescriptionCompat =
        if (windowIndex < service.mediaSource.mediasMetadata.size) {
            service.mediaSource.mediasMetadata[windowIndex].description
        } else MediaDescriptionCompat.Builder().build()
}