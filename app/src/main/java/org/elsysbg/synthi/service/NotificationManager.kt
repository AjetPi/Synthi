package org.elsysbg.synthi.service

import android.content.Context
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat.Token
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import org.elsysbg.synthi.R
import org.elsysbg.synthi.util.Constants

class NotificationManager(
    context: Context,
    sessionToken: Token,
    notificationListener: PlayerNotificationManager.NotificationListener
) {
    private val notificationManager: PlayerNotificationManager

    init {
        val mediaController = MediaControllerCompat(context, sessionToken)
        notificationManager = PlayerNotificationManager
            .Builder(context, Constants.PLAYBACK_NOTIFICATION_ID, Constants.PLAYBACK_NOTIFICATION_CHANNEL_ID)
            .apply {
                setChannelNameResourceId(R.string.app_name)
                setChannelDescriptionResourceId(R.string.songs)
                setMediaDescriptionAdapter(MediaDescriptionAdapterImpl(mediaController))
                setNotificationListener(notificationListener)
            }
            .build()
            .apply {
                setMediaSessionToken(sessionToken)
                setUseRewindAction(false)
                setUseFastForwardAction(false)
            }
    }

    fun startNotification(player: Player) {
        notificationManager.setPlayer(player)
    }

    fun cancelNotification() {
        notificationManager.setPlayer(null)
    }
}