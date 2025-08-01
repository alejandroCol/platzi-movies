package platzi.movies.feature.detail.data.repository

import platzi.movies.feature.detail.data.remote.source.DetailRemoteDataSource
import platzi.movies.feature.detail.domain.models.MovieDetail
import platzi.movies.feature.detail.domain.models.MovieTrailer
import platzi.movies.feature.detail.domain.repository.DetailRepository
import javax.inject.Inject


class DetailRepositoryImpl @Inject constructor(
    private val remoteDataSource: DetailRemoteDataSource
) : DetailRepository {

    override suspend fun getMovieDetail(movieId: Int): MovieDetail {
        return remoteDataSource.fetchMovieDetail(movieId)
    }

    override suspend fun getMovieTrailer(movieId: Int): MovieTrailer {
        return remoteDataSource.fetchMovieTrailer(movieId)
    }
}