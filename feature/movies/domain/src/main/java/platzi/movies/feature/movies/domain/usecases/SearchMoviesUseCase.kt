package platzi.movies.feature.movies.domain.usecases

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import platzi.movies.feature.movies.domain.models.Movie
import platzi.movies.feature.movies.domain.models.MovieQuery
import platzi.movies.feature.movies.domain.repository.MovieRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(query: MovieQuery): Flow<PagingData<Movie>> {
        return movieRepository.searchMovies(query.query)
    }
}