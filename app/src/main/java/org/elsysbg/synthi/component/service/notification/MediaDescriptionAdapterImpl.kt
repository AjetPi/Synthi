package org.elsysbg.synthi.component.service.notification

import android.app.PendingIntent
import android.graphics.Bitmap
import android.support.v4.media.session.MediaControllerCompat
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class MediaDescriptionAdapterImpl(private val controller: MediaControllerCompat) : PlayerNotificationManager.MediaDescriptionAdapter {
    override fun getCurrentContentTitle(player: Player) = controller.metadata.description.title.toString()
    override fun createCurrentContentIntent(player: Player): PendingIntent? = controller.sessionActivity
    override fun getCurrentContentText(player: Player): CharSequence? = controller.metadata.description.subtitle

    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? = null
}