package org.elsysbg.synthi.service

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import org.elsysbg.synthi.data.repository.MediaRepositoryImpl
import javax.inject.Inject

typealias onReadyListener = (Boolean) -> Unit

enum class MediaSourceState {
    STATE_CREATED,
    STATE_INITIALIZING,
    STATE_INITIALIZED
}

class MediaSource @Inject constructor(private val repository: MediaRepositoryImpl) {
    var mediasMetadata = emptyList<MediaMetadataCompat>()
    private val listeners = mutableListOf<onReadyListener>()
    private var state: MediaSourceState = MediaSourceState.STATE_CREATED
        set(value) {
            field = value
            if (value == MediaSourceState.STATE_INITIALIZED) {
                synchronized(listeners) {
                    listeners.forEach { listener ->
                        listener(true)
                    }
                }
            }
        }

    fun whenReady(listener: onReadyListener): Boolean {
        return if (state == MediaSourceState.STATE_INITIALIZED) {
            listener(true)
            true
        } else {
            listeners += listener
            false
        }
    }

    suspend fun setMediasMetadata() {
        state = MediaSourceState.STATE_INITIALIZING
        val medias = repository.getMedias()
        mediasMetadata = medias.map { media ->
            MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, media.contentUri.toString())
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, media.id.toString())
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, media.duration.toLong())
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, media.title)
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, media.artist)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, media.album)
                .putLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER, media.track.toLong())
                .putString(MediaMetadataCompat.METADATA_KEY_ART_URI, media.coverUri.toString())
                .build()
        }
        state = MediaSourceState.STATE_INITIALIZED
    }

    fun getMediaSource(dataSource: CacheDataSource.Factory): ConcatenatingMediaSource {
        val concatenatingMediaSource = ConcatenatingMediaSource()
        mediasMetadata.forEach { mediaMetadata ->
            val mediaItem = MediaItem.fromUri(mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI))
            val mediaSource = ProgressiveMediaSource
                .Factory(dataSource)
                .createMediaSource(mediaItem)
            concatenatingMediaSource.addMediaSource(mediaSource)
        }
        return concatenatingMediaSource
    }

    fun getMediaItems() = mediasMetadata.map { mediaMetadata ->
        val description = MediaDescriptionCompat.Builder()
            .setMediaUri(mediaMetadata.description.mediaUri)
            .setMediaId(mediaMetadata.description.mediaId)
            .setTitle(mediaMetadata.description.title)
            .setSubtitle(mediaMetadata.description.subtitle)
            .setIconUri(mediaMetadata.description.iconUri)
            .build()
        MediaBrowserCompat.MediaItem(description, MediaBrowserCompat.MediaItem.FLAG_PLAYABLE)
    }.toMutableList()
}