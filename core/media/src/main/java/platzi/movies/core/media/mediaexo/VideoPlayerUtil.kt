package platzi.movies.core.media.mediaexo

import android.content.Context
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.LoadControl
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.trackselection.AdaptiveTrackSelection
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.exoplayer.trackselection.TrackSelector
import androidx.media3.exoplayer.upstream.DefaultAllocator

@androidx.media3.common.util.UnstableApi
object VideoPlayerUtil {
    private const val MIN_BUFFER_MS = 20_000
    private const val MAX_BUFFER_MS = 20_000
    private const val BUFFER_FOR_PLAYBACK_MS = 500
    private const val BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS = 1_000
    const val CONNECT_TIMEOUT = 4000
    const val READ_TIMEOUT = 4000
    const val CACHE_SIZE: Long = 25 * 1024 * 1024

    interface ExoPlayerFactory {
        fun createExoPlayer(context: Context): ExoPlayer
        fun createBuilder(context: Context): ExoPlayer.Builder
    }

    private val defaultFactory = object : ExoPlayerFactory {
        override fun createExoPlayer(context: Context): ExoPlayer {
            val renderersFactory = DefaultRenderersFactory(context)
                .setEnableDecoderFallback(true)
            return ExoPlayer.Builder(context)
                .setLoadControl(createLoadControl())
                .setTrackSelector(createTrackSelector(context))
                .setRenderersFactory(renderersFactory)
                .build()
        }

        override fun createBuilder(context: Context): ExoPlayer.Builder {
            return ExoPlayer.Builder(context)
        }
    }

    var exoPlayerFactory: ExoPlayerFactory = defaultFactory

    fun createConfiguredExoPlayer(context: Context): ExoPlayer {
        return exoPlayerFactory.createExoPlayer(context)
    }

    fun createLoadControl(): LoadControl {
        return DefaultLoadControl.Builder()
            .setAllocator(DefaultAllocator(true, C.DEFAULT_BUFFER_SEGMENT_SIZE))
            .setBufferDurationsMs(
                MIN_BUFFER_MS,
                MAX_BUFFER_MS,
                BUFFER_FOR_PLAYBACK_MS,
                BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS
            ).build()
    }

    fun createTrackSelector(context: Context): TrackSelector {
        val adaptiveTrackSelectionFactory = AdaptiveTrackSelection.Factory()
        return DefaultTrackSelector(context, adaptiveTrackSelectionFactory)
    }

    fun createMediaSource(url: String, cache: Cache?): MediaSource {
        val dataSourceFactory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)
            .setConnectTimeoutMs(CONNECT_TIMEOUT)
            .setReadTimeoutMs(READ_TIMEOUT)
        val cacheDataSourceFactory = cache?.let { createCacheDataSourceFactory(it, dataSourceFactory) }
        return ProgressiveMediaSource.Factory(cacheDataSourceFactory ?: dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(url))
    }

    fun createCache(context: Context): Cache {
        return SimpleCache(
            context.cacheDir,
            LeastRecentlyUsedCacheEvictor(CACHE_SIZE),
            StandaloneDatabaseProvider(context)
        )
    }

    fun createCacheDataSourceFactory(
        cache: Cache,
        dataSourceFactory: DefaultHttpDataSource.Factory
    ): CacheDataSource.Factory {
        return CacheDataSource.Factory()
            .setCache(cache)
            .setUpstreamDataSourceFactory(dataSourceFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
    }
}
