package platzi.movies.core.media

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import coil.compose.AsyncImage
import platzi.movies.core.media.model.VideoModel
import platzi.movies.core.media.utils.toExoPlayerState

const val TAG_INLINE_VIDEO_PLAYER = "TAG_INLINE_VIDEO_PLAYER"
const val TAG_INLINE_VIDEO_PLAYER_IMAGE_THUMBNAIL = "TAG_INLINE_VIDEO_PLAYER_IMAGE_THUMBNAIL"

@OptIn(UnstableApi::class)
@Composable
fun MoviePlayerView(
    modifier: Modifier = Modifier,
    video: VideoModel,
    enabled: Boolean = true,
) {
    Box(
        modifier = modifier
            .testTag(TAG_INLINE_VIDEO_PLAYER)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
        ) {

            if (video.showThumbnail) {
                AsyncImage(
                    model = video.thumbnailUrl,
                    contentDescription = "Movie Poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .testTag(TAG_INLINE_VIDEO_PLAYER_IMAGE_THUMBNAIL),
                    placeholder = painterResource(R.drawable.ic_error_load_image),
                    error = painterResource(R.drawable.ic_error_load_image)
                )
            }
            if (enabled) {
                VideoPlayer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    state = video.toExoPlayerState(),
                )
            }
        }
    }
}