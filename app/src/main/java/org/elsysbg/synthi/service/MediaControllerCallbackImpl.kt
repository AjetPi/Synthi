package org.elsysbg.synthi.service

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat

class MediaControllerCallbackImpl(private val serviceConnection: MediaServiceConnection) : MediaControllerCompat.Callback() {
    override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
        serviceConnection.playbackState.value = state
    }

    override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
        serviceConnection.currentMediaMetadata.value = metadata
    }

    override fun onSessionDestroyed() {
        serviceConnection.mediaBrowserConnectionCallback.onConnectionSuspended()
    }
}