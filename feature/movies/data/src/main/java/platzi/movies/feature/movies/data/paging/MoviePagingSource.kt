package platzi.movies.feature.movies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import platzi.movies.feature.movies.data.dto.MovieQueryDto
import platzi.movies.feature.movies.data.local.source.MovieLocalDataSource
import platzi.movies.feature.movies.data.mapper.MovieMapper.toDomain
import platzi.movies.feature.movies.data.mapper.MovieMapper.toEntity
import platzi.movies.feature.movies.data.remote.source.MovieRemoteDataSource
import platzi.movies.feature.movies.domain.models.Movie

class MoviePagingSource(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource,
    private val movieQuery: MovieQueryDto
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        movieQuery.page = page

        return runCatching {
            val movies = remoteDataSource.fetchPopularMovies(movieQuery)
            localDataSource.saveMovies(movies.map { it.toEntity() })
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        }.getOrElse {
            val cachedMovies = localDataSource.getCachedMovies().map { it.toDomain() }
            if (cachedMovies.isNotEmpty()) {
                LoadResult.Page(data = cachedMovies, prevKey = null, nextKey = null)
            } else {
                LoadResult.Error(it)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}