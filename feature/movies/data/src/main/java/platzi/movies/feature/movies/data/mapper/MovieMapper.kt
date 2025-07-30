package platzi.movies.feature.movies.data.mapper

import platzi.movies.feature.movies.data.dto.MovieDto
import platzi.movies.feature.movies.data.dto.MovieQueryDto
import platzi.movies.feature.movies.data.local.entity.MovieEntity
import platzi.movies.feature.movies.domain.models.Movie
import platzi.movies.feature.movies.domain.models.MovieQuery


object MovieMapper {
    fun MovieDto.toDomain(): Movie {
        return Movie(
            id = id,
            title = title,
            posterUrl = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: "",
            overview = overview,
            releaseDate = releaseDate,
            runtime = 0,
            rating = rating
        )
    }

    fun MovieQuery.toDto(): MovieQueryDto {
        return MovieQueryDto(page = this.page, language = this.language, query = this.query)
    }

    fun MovieEntity.toDomain(): Movie {
        return Movie(
            id = id,
            title = title ?: "",
            posterUrl = posterUrl ?: "",
            overview = overview ?: "",
            releaseDate = releaseDate ?: "",
            runtime = 0,
            rating = rating ?: 0.0
        )
    }

    fun Movie.toEntity(): MovieEntity {
        return MovieEntity(
            id = this.id,
            title = this.title,
            posterUrl = this.posterUrl,
            overview = this.overview,
            releaseDate = this.releaseDate,
            runtime = 0,
            rating = this.rating
        )
    }
}