package platzi.movies.core.media

import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.C
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.datasource.cache.Cache
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.util.EventLogger
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.ui.Modifier
import androidx.media3.ui.AspectRatioFrameLayout
import platzi.movies.core.media.mediaexo.ExoPlayerSection
import platzi.movies.core.media.mediaexo.VideoPlayerState
import platzi.movies.core.media.mediaexo.VideoPlayerUtil.createCache
import platzi.movies.core.media.mediaexo.VideoPlayerUtil.createConfiguredExoPlayer
import platzi.movies.core.media.mediaexo.VideoPlayerUtil.createMediaSource
import platzi.movies.core.media.youtube.YouTubePlayerSection

const val PLAYER_VIEW_CONTAINER = "PLAYER_VIEW_CONTAINER"

@SuppressWarnings("LongMethod")
@androidx.media3.common.util.UnstableApi
@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    state: VideoPlayerState,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    when {
        state.videoId.isNotEmpty() -> {
            YouTubePlayerSection(
                videoId = state.videoId,
                modifier = modifier
            )
        }

        state.videoUrl.isNotEmpty() -> {
            ExoPlayerSection(
                modifier = modifier,
                state = state,
                dispatcher = dispatcher
            )
        }
    }
}

@androidx.media3.common.util.UnstableApi
@Composable
fun ConfigureExoPlayerEffect(
    context: Context,
    videoUrl: String,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    onExoPlayerCreated: (ExoPlayer, Cache?) -> Unit
) {
    LaunchedEffect(videoUrl) {
        withContext(dispatcher) {
            val cache = createCache(context = context)
            val mediaSource = createMediaSource(url = videoUrl, cache = cache)
            val isDebuggable = 0 != context.applicationInfo.flags and
                    ApplicationInfo.FLAG_DEBUGGABLE

            val newExoPlayer = createConfiguredExoPlayer(context).apply {
                if (isDebuggable) {
                    addAnalyticsListener(EventLogger())
                }
            }

            withContext(Dispatchers.Main) {
                newExoPlayer.apply {
                    repeatMode = Player.REPEAT_MODE_ONE
                    setMediaSource(mediaSource)
                    prepare()
                }.also {
                    onExoPlayerCreated(it, cache)
                }
            }
        }
    }
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun ExoPlayerListenerEffect(
    exoPlayer: ExoPlayer,
) {
    LaunchedEffect(exoPlayer) {
        exoPlayer.addListener(
            object : Player.Listener {
                override fun onPlayerError(error: PlaybackException) {
                    if (error.errorCode == PlaybackException.ERROR_CODE_DECODING_FAILED) {
                        exoPlayer.stop()
                        exoPlayer.prepare()
                        exoPlayer.seekTo(C.TIME_UNSET)
                    }
                }
            }
        )
    }
}

@Composable
fun ExoPlayerDisposeEffect(
    lifecycleOwner: LifecycleOwner,
    exoPlayer: ExoPlayer,
    onLifecycleEventChange: (Lifecycle.Event) -> Unit
) {
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event -> onLifecycleEventChange(event) }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            exoPlayer.release()
        }
    }
}

@Composable
fun ExoPlayerPlaybackEffect(
    exoPlayer: ExoPlayer,
    videoPlayerState: VideoPlayerState
) {
    LaunchedEffect(videoPlayerState.shouldPlay) {
        if (videoPlayerState.shouldPlay) {
            exoPlayer.volume = if (videoPlayerState.muted) 0f else 1f
            exoPlayer.play()
        } else {
            exoPlayer.pause()
        }
    }

    LaunchedEffect(videoPlayerState.muted) {
        exoPlayer.volume = if (videoPlayerState.muted) 0f else 1f
    }
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun PlayerView(
    modifier: Modifier,
    exoPlayer: ExoPlayer,
    lifecycleEvent: Lifecycle.Event,
    autoplay: Boolean
) {
    Box(modifier = modifier) {
        AndroidView(
            modifier = modifier
                .testTag(PLAYER_VIEW_CONTAINER),
            factory = {
                PlayerView(it).apply {
                    this.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT
                    this.setShutterBackgroundColor(Color.White.toArgb())
                    this.useController = true
                    this.player = exoPlayer
                }
            },
            update = {
                when (lifecycleEvent) {
                    Lifecycle.Event.ON_RESUME -> {
                        if (autoplay && it.player?.isPlaying == false) {
                            it.onResume()
                            it.player?.play()
                        }
                    }

                    Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_STOP -> {
                        if (it.player?.isPlaying == true) {
                            it.onPause()
                            it.player?.pause()
                        }
                    }

                    else -> Unit
                }
            }
        )
    }
}