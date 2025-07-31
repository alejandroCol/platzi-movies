package platzi.movies.feature.detail.data.mapper

import platzi.movies.feature.detail.data.dto.MovieDetailDto
import platzi.movies.feature.detail.domain.models.MovieDetail

object DetailMapper {
    fun MovieDetailDto.toDomain() = MovieDetail(
        id = id,
        title = title ?: "",
        posterUrl = "https://image.tmdb.org/t/p/w500$posterPath",
        backdropUrl = "https://image.tmdb.org/t/p/w500$backdropPath",
        overview = overview ?: "",
        releaseDate = releaseDate,
        runtime = runtime,
        rating = rating,
        budget = budget,
        revenue = revenue,
        tagline = tagline
    )
}