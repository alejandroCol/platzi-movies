package platzi.movies.core.media.utils

import platzi.movies.core.media.model.VideoModel
import platzi.movies.core.media.mediaexo.VideoPlayerState

const val EMPTY = ""

fun VideoModel.toExoPlayerState(): VideoPlayerState {
    return VideoPlayerState(
        videoUrl = url,
        shouldPlay = play,
        muted = muted,
        videoId = id
    )
}