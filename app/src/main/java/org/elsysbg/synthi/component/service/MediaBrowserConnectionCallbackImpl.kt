package org.elsysbg.synthi.component.service

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat

class MediaBrowserConnectionCallbackImpl(private val connection: MediaServiceConnection) : MediaBrowserCompat.ConnectionCallback() {
    override fun onConnected() {
        connection.apply {
            controller = MediaControllerCompat(context, browser.sessionToken).apply { registerCallback(MediaControllerCallbackImpl(connection)) }
            isConnected.value = true
        }
    }

    override fun onConnectionSuspended() {
        connection.isConnected.value = false
    }

    override fun onConnectionFailed() {
        connection.isConnected.value = false
    }
}