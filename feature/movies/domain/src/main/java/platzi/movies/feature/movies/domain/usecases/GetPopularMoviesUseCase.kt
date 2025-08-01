package platzi.movies.feature.movies.domain.usecases

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import platzi.movies.feature.movies.domain.models.Movie
import platzi.movies.feature.movies.domain.models.MovieQuery
import platzi.movies.feature.movies.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieQuery: MovieQuery): Flow<PagingData<Movie>> {
        return repository.getPopularMovies(movieQuery)
    }
}