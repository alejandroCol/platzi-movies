package platzi.movies.feature.detail.domain.usecases

import platzi.movies.feature.detail.domain.models.MovieDetail
import platzi.movies.feature.detail.domain.repository.DetailRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: DetailRepository
) {
    suspend operator fun invoke(movieId: Int): Result<MovieDetail> {
        return runCatching { repository.getMovieDetail(movieId) }
    }
}
