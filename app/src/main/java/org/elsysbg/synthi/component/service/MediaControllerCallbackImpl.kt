package org.elsysbg.synthi.component.service

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat

class MediaControllerCallbackImpl(private val connection: MediaServiceConnection) : MediaControllerCompat.Callback() {
    override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
        connection.playbackState.value = state
    }

    override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
        connection.currentMediaMetadata.value = metadata
    }

    override fun onSessionDestroyed() = connection.mediaBrowserConnectionCallback.onConnectionSuspended()
}