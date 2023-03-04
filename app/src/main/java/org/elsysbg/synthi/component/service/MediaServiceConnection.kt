package org.elsysbg.synthi.component.service

import android.content.ComponentName
import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MediaServiceConnection @Inject constructor(@ApplicationContext val context: Context) {
    val playbackState = mutableStateOf<PlaybackStateCompat?>(null)
    val isConnected = mutableStateOf(false)
    val currentMediaMetadata = mutableStateOf<MediaMetadataCompat?>(null)

    lateinit var controller: MediaControllerCompat

    val mediaBrowserConnectionCallback = MediaBrowserConnectionCallbackImpl(this)
    val browser = MediaBrowserCompat(
        context,
        ComponentName(context, MediaService::class.java),
        mediaBrowserConnectionCallback,
        null
    ).apply { connect() }

    val controls: MediaControllerCompat.TransportControls
        get() = controller.transportControls

    fun subscribe(
        parentId:String,
        callback: MediaBrowserCompat.SubscriptionCallback
    ) = browser.subscribe(parentId, callback)

    fun unsubscribe(
        parentId:String,
        callback: MediaBrowserCompat.SubscriptionCallback
    ) = browser.unsubscribe(parentId, callback)
}