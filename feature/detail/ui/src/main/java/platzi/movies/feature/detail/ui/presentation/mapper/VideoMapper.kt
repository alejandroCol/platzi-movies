package platzi.movies.feature.detail.ui.presentation.mapper

import platzi.movies.core.media.model.VideoModel
import platzi.movies.feature.detail.domain.models.MovieTrailer

fun MovieTrailer.toVideoModel(): VideoModel {
    return VideoModel(
        url = this.videoUrl,
        id = this.videoId,
        play = true,
    )
}