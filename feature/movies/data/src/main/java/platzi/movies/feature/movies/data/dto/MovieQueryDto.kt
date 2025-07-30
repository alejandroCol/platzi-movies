package platzi.movies.feature.movies.data.dto

data class MovieQueryDto(
    var page: Int = 1,
    val language: String = "en",
    val query: String = ""
)