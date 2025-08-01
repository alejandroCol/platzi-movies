package platzi.movies.core.media.youtube

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.compose.ui.Modifier
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YouTubePlayerSection(
    videoId: String,
    modifier: Modifier = Modifier,
    onReady: () -> Unit = {},
    onError: (String) -> Unit = {}
) {
    val context = LocalContext.current
    var lifecycle by remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            YouTubePlayerView(ctx).apply {
                enableAutomaticInitialization = false
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(videoId, 0f)
                        onReady()
                    }

                    override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                        onError("YouTube Player Error: $error")
                    }
                })
            }
        },
        update = { view ->
            when(lifecycle) {
                Lifecycle.Event.ON_DESTROY -> view.release()
                else -> Unit
            }
        }
    )

    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        (context as? LifecycleOwner)?.lifecycle?.addObserver(observer)

        onDispose {
            (context as? LifecycleOwner)?.lifecycle?.removeObserver(observer)
        }
    }
}