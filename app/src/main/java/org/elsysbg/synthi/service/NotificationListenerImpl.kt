package org.elsysbg.synthi.service

import android.app.Notification
import android.content.Intent
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class NotificationListenerImpl(private val service: MediaService) : PlayerNotificationManager.NotificationListener {
    override fun onNotificationCancelled(
        notificationId: Int,
        dismissedByUser: Boolean
    ) {
        service.apply {
            stopForeground(notificationId)
            isForegroundService = false
            stopSelf()
        }
    }

    override fun onNotificationPosted(
        notificationId: Int,
        notification: Notification,
        ongoing: Boolean
    ) {
        service.apply {
            if (ongoing && !isForegroundService) {
                ContextCompat.startForegroundService(applicationContext, Intent(applicationContext, this.javaClass))
                startForeground(notificationId, notification)
                isForegroundService = true
            }
        }
    }
}