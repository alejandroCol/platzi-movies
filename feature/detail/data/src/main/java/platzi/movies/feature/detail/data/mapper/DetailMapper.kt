package platzi.movies.feature.detail.data.mapper

import platzi.movies.feature.detail.data.dto.MovieDetailDto
import platzi.movies.feature.detail.data.dto.ResponseTrailerDto
import platzi.movies.feature.detail.domain.models.MovieDetail
import platzi.movies.feature.detail.domain.models.MovieTrailer

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

    fun ResponseTrailerDto.toDomain(): MovieTrailer {
        val trailer = results.firstOrNull()
        return MovieTrailer(
            videoUrl = "https://www.youtube.com/watch?v=${trailer?.key}",
            isOfficial = trailer?.isOfficial ?: false,
            videoId = trailer?.key?: ""
        )
    }
}