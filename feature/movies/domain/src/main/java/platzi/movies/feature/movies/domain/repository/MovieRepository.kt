package platzi.movies.feature.movies.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import platzi.movies.feature.movies.domain.models.Movie
import platzi.movies.feature.movies.domain.models.MovieQuery

interface MovieRepository {
    fun getPopularMovies(query: MovieQuery): Flow<PagingData<Movie>>
    fun searchMovies(query: String): Flow<PagingData<Movie>>
}