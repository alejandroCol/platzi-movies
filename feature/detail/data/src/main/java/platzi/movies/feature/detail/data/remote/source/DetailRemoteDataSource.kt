package platzi.movies.feature.detail.data.remote.source

import platzi.movies.feature.detail.domain.models.MovieDetail
import platzi.movies.feature.detail.domain.models.MovieTrailer

interface DetailRemoteDataSource {
    suspend fun fetchMovieDetail(movieId: Int): MovieDetail
    suspend fun fetchMovieTrailer(movieId: Int): MovieTrailer
}