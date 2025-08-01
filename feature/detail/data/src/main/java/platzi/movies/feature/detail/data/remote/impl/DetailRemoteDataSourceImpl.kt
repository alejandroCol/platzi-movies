package platzi.movies.feature.detail.data.remote.impl

import platzi.movies.feature.detail.data.mapper.DetailMapper.toDomain
import platzi.movies.feature.detail.data.remote.api.DetailApiService
import platzi.movies.feature.detail.data.remote.source.DetailRemoteDataSource
import platzi.movies.feature.detail.domain.models.MovieDetail
import platzi.movies.feature.detail.domain.models.MovieTrailer
import javax.inject.Inject

class DetailRemoteDataSourceImpl @Inject constructor(
    private val apiService: DetailApiService
) : DetailRemoteDataSource {
    override suspend fun fetchMovieDetail(movieId: Int): MovieDetail {
        return apiService.getMovieDetail(movieId).toDomain()
    }

    override suspend fun fetchMovieTrailer(movieId: Int): MovieTrailer {
        return apiService.getMovieTrailer(movieId).toDomain()
    }
}
