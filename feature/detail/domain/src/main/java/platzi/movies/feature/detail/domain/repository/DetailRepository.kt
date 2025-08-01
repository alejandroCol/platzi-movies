package platzi.movies.feature.detail.domain.repository

import platzi.movies.feature.detail.domain.models.MovieDetail
import platzi.movies.feature.detail.domain.models.MovieTrailer

interface DetailRepository {
    suspend fun getMovieDetail(movieId: Int): MovieDetail
    suspend fun getMovieTrailer(movieId: Int): MovieTrailer
}