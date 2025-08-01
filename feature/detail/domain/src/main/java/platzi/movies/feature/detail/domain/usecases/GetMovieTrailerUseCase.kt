package platzi.movies.feature.detail.domain.usecases

import platzi.movies.feature.detail.domain.models.MovieTrailer
import platzi.movies.feature.detail.domain.repository.DetailRepository
import javax.inject.Inject

class GetMovieTrailerUseCase @Inject constructor(
    private val repository: DetailRepository
) {
    suspend operator fun invoke(movieId: Int): Result<MovieTrailer> {
        return runCatching { repository.getMovieTrailer(movieId) }
    }
}