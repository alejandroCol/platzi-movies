package platzi.movies.feature.detail.domain.repository

import platzi.movies.feature.detail.domain.models.MovieDetail

interface DetailRepository {
    suspend fun getMovieDetail(movieId: Int): MovieDetail
}