package platzi.movies.core.media.mediaexo

import androidx.media3.common.C
import platzi.movies.core.media.utils.EMPTY

data class VideoPlayerState(
    val videoId: String = EMPTY,
    val videoUrl: String = EMPTY,
    val shouldPlay: Boolean = false,
    val muted: Boolean = false,
    val startPosition: Long = C.TIME_UNSET
)