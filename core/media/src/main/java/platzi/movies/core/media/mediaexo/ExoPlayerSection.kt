package platzi.movies.core.media.mediaexo

import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.media3.datasource.cache.Cache
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.CoroutineDispatcher
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import platzi.movies.core.media.ConfigureExoPlayerEffect
import platzi.movies.core.media.ExoPlayerDisposeEffect
import platzi.movies.core.media.ExoPlayerListenerEffect
import platzi.movies.core.media.ExoPlayerPlaybackEffect
import platzi.movies.core.media.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun ExoPlayerSection(
    modifier: Modifier = Modifier,
    state: VideoPlayerState,
    dispatcher: CoroutineDispatcher
) {
    val context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    var exoPlayer by remember { mutableStateOf<ExoPlayer?>(null) }
    var cache by remember { mutableStateOf<Cache?>(null) }

    ConfigureExoPlayerEffect(
        context = context,
        videoUrl = state.videoUrl,
        dispatcher = dispatcher
    ) { createdExoPlayer, createdCache ->
        cache = createdCache
        exoPlayer = createdExoPlayer
    }

    exoPlayer?.let { safeExo ->
        ExoPlayerListenerEffect(
            exoPlayer = safeExo
        )

        var lifecycleEvent by remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }
        ExoPlayerDisposeEffect(
            lifecycleOwner = lifecycleOwner,
            exoPlayer = safeExo,
            onLifecycleEventChange = { event -> lifecycleEvent = event }
        )

        ExoPlayerPlaybackEffect(
            exoPlayer = safeExo,
            videoPlayerState = state,
        )

        PlayerView(
            modifier = modifier,
            exoPlayer = safeExo,
            lifecycleEvent = lifecycleEvent,
            autoplay = state.shouldPlay
        )
    }
}