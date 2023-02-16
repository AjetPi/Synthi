package org.elsysbg.synthi.service

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat

class MediaBrowserConnectionCallbackImpl(private val serviceConnection: MediaServiceConnection) : MediaBrowserCompat.ConnectionCallback() {
    override fun onConnected() {
        serviceConnection.apply {
            mediaController = MediaControllerCompat(context, mediaBrowser.sessionToken).apply {
                registerCallback(MediaControllerCallbackImpl(serviceConnection))
            }
            isConnected.value = true
        }
    }

    override fun onConnectionSuspended() {
        serviceConnection.isConnected.value = false
    }

    override fun onConnectionFailed() {
        serviceConnection.isConnected.value = false
    }
}