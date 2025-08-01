package platzi.movies.feature.detail.domain.models

data class MovieTrailer(
    val videoUrl: String,
    val videoId: String,
    val isOfficial: Boolean = false
)
