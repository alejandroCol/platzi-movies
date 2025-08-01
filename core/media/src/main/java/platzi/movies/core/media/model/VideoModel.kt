package platzi.movies.core.media.model

import platzi.movies.core.media.utils.EMPTY

data class VideoModel(
    val id: String = EMPTY,
    val url: String,
    val play: Boolean = false,
    val muted: Boolean = true,
    val thumbnailUrl: String = EMPTY,
    val showThumbnail: Boolean = false,
)