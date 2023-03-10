package org.elsysbg.synthi.component.service

import android.app.PendingIntent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import org.elsysbg.synthi.component.service.notification.NotificationListenerImpl
import org.elsysbg.synthi.component.service.notification.NotificationManager
import org.elsysbg.synthi.component.service.player.PlaybackPreparerImpl
import org.elsysbg.synthi.component.service.player.PlayerListenerImpl
import org.elsysbg.synthi.component.service.player.TimelineQueueNavigatorImpl
import org.elsysbg.synthi.domain.util.Constants
import javax.inject.Inject

@AndroidEntryPoint
class MediaService : MediaBrowserServiceCompat() {
    @Inject lateinit var dataSourceFactory: CacheDataSource.Factory
    @Inject lateinit var mediaSource: MediaSource
    @Inject lateinit var player: ExoPlayer

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    lateinit var session: MediaSessionCompat
    private lateinit var sessionConnector: MediaSessionConnector

    var isForegroundService = false
    var currentMetadata: MediaMetadataCompat? = null
    lateinit var notificationManager: NotificationManager

    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?) =
        BrowserRoot(Constants.MEDIA_ROOT_ID, null)

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        when (parentId) {
            Constants.MEDIA_ROOT_ID -> {
                val resultsSent = mediaSource.whenReady { isInitialized ->
                    if (isInitialized) result.sendResult(mediaSource.getMediaItems())
                    else result.sendResult(null)
                }
                if (!resultsSent) result.detach()
            }
            else -> Unit
        }
    }

    override fun onCreate() {
        super.onCreate()
        serviceScope.launch { mediaSource.setMetadata() }

        val activityIntent = packageManager.getLaunchIntentForPackage(packageName).let { intent ->
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }
        session = MediaSessionCompat(this, Constants.SERVICE_TAG).apply {
            setSessionActivity(activityIntent)
            setSessionToken(sessionToken)
            isActive = true
        }
        sessionConnector = MediaSessionConnector(session).apply {
            setPlaybackPreparer(PlaybackPreparerImpl(this@MediaService))
            setQueueNavigator(TimelineQueueNavigatorImpl(this@MediaService))
            setPlayer(player)
        }

        notificationManager = NotificationManager(
            this,
            session.sessionToken,
            NotificationListenerImpl(this)
        )
        notificationManager.startNotification(player)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        player.release()
    }

    fun prepare(
        metadataList: List<MediaMetadataCompat>,
        itemToPlay: MediaMetadataCompat?,
        playWhenReady: Boolean
    ) {
        val indexToPlay = if (currentMetadata == null) 0 else metadataList.indexOf(itemToPlay)
        player.apply {
            addListener(PlayerListenerImpl(this@MediaService))
            setMediaSource(mediaSource.getMediaSource(dataSourceFactory))
            prepare()
            seekTo(indexToPlay, 0)
            this.playWhenReady = playWhenReady
        }
    }
}