package platzi.movies.feature.movies.data.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import platzi.movies.feature.movies.data.local.source.MovieLocalDataSource
import platzi.movies.feature.movies.data.mapper.MovieMapper.toDto
import platzi.movies.feature.movies.data.paging.MoviePagingSource
import platzi.movies.feature.movies.data.paging.MovieSearchPagingSource
import platzi.movies.feature.movies.data.remote.source.MovieRemoteDataSource
import platzi.movies.feature.movies.domain.models.Movie
import platzi.movies.feature.movies.domain.models.MovieQuery
import platzi.movies.feature.movies.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
) : MovieRepository {

    override fun getPopularMovies(query: MovieQuery): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(remoteDataSource, localDataSource, query.toDto()) }
        ).flow
    }

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { MovieSearchPagingSource(localDataSource, query) }
        ).flow
    }
}