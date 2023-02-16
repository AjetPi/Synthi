package org.elsysbg.synthi.service

import android.app.PendingIntent
import android.graphics.Bitmap
import android.support.v4.media.session.MediaControllerCompat
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class MediaDescriptionAdapterImpl(private val mediaController: MediaControllerCompat) : PlayerNotificationManager.MediaDescriptionAdapter {
    override fun getCurrentContentTitle(player: Player): CharSequence = mediaController.metadata.description.title.toString()
    override fun createCurrentContentIntent(player: Player): PendingIntent? = mediaController.sessionActivity
    override fun getCurrentContentText(player: Player): CharSequence? = mediaController.metadata.description.subtitle

    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? = null
}