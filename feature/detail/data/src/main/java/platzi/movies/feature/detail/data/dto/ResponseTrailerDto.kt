package platzi.movies.feature.detail.data.dto

data class ResponseTrailerDto(
    val id: Int,
    val results: List<MovieTrailerDto>
)