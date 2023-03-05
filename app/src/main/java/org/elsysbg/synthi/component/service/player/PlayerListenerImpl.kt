package org.elsysbg.synthi.component.service.player

import com.google.android.exoplayer2.Player
import org.elsysbg.synthi.component.service.MediaService

class PlayerListenerImpl(private val service: MediaService) : Player.Listener {
    override fun onPlaybackStateChanged(playbackState: Int) =
        when (playbackState) {
            Player.STATE_BUFFERING,
            Player.STATE_READY -> service.notificationManager.startNotification(service.exoPlayer)
            else -> service.notificationManager.cancelNotification()
        }
}