package platzi.movies.feature.movies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import platzi.movies.feature.movies.data.local.source.MovieLocalDataSource
import platzi.movies.feature.movies.data.mapper.MovieMapper.toDomain
import platzi.movies.feature.movies.domain.models.Movie

class MovieSearchPagingSource(
    private val localDataSource: MovieLocalDataSource,
    private val query: String
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val movies = localDataSource.searchMovies(query).map { it.toDomain() }
            LoadResult.Page(
                data = movies,
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? = null
}