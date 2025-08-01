package platzi.movies.feature.movies.data.remote.source

import platzi.movies.feature.movies.data.dto.MovieQueryDto
import platzi.movies.feature.movies.domain.models.Movie

interface MovieRemoteDataSource {
    suspend fun fetchPopularMovies(movieQuery: MovieQueryDto): List<Movie>
}