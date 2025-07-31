package platzi.movies.feature.detail.data.remote.source

import platzi.movies.feature.detail.domain.models.MovieDetail

interface DetailRemoteDataSource {
    suspend fun fetchMovieDetail(movieId: Int): MovieDetail
}