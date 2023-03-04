package org.elsysbg.synthi.component.service.notification

import android.content.Context
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat.Token
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import org.elsysbg.synthi.R
import org.elsysbg.synthi.domain.util.Constants

class NotificationManager(
    context: Context,
    sessionToken: Token,
    notificationListener: PlayerNotificationManager.NotificationListener
) {
    private val manager: PlayerNotificationManager

    init {
        val mediaController = MediaControllerCompat(context, sessionToken)
        manager = PlayerNotificationManager
            .Builder(context, Constants.PLAYBACK_NOTIFICATION_ID, Constants.PLAYBACK_NOTIFICATION_CHANNEL_ID)
            .apply {
                setChannelNameResourceId(R.string.app_name)
                setMediaDescriptionAdapter(MediaDescriptionAdapterImpl(mediaController))
                setNotificationListener(notificationListener)
            }
            .build()
            .apply {
                setMediaSessionToken(sessionToken)
                setUseRewindAction(false)
                setUseFastForwardAction(false)
                setUseNextActionInCompactView(true)
            }
    }

    fun startNotification(player: Player) = manager.setPlayer(player)

    fun cancelNotification() = manager.setPlayer(null)
}