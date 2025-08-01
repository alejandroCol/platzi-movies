package platzi.movies.feature.movies.data.remote.impl

import platzi.movies.feature.movies.data.dto.MovieQueryDto
import platzi.movies.feature.movies.data.mapper.MovieMapper.toDomain
import platzi.movies.feature.movies.data.remote.api.MovieApiService
import platzi.movies.feature.movies.data.remote.source.MovieRemoteDataSource
import platzi.movies.feature.movies.domain.models.Movie
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val apiService: MovieApiService
) : MovieRemoteDataSource {
    override suspend fun fetchPopularMovies(movieQuery: MovieQueryDto): List<Movie> {
        return apiService.getPopularMovies(
            page = movieQuery.page,
            language = movieQuery.language
        ).results.map { it.toDomain() }
    }
}
