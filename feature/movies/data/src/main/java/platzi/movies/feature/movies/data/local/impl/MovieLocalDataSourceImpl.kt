package platzi.movies.feature.movies.data.local.impl

import platzi.movies.feature.movies.data.local.dao.MovieDao
import platzi.movies.feature.movies.data.local.entity.MovieEntity
import platzi.movies.feature.movies.data.local.source.MovieLocalDataSource
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
) : MovieLocalDataSource {

    override suspend fun getCachedMovies(): List<MovieEntity> {
        return movieDao.getMovies()
    }

    override suspend fun searchMovies(query: String): List<MovieEntity> {
        return movieDao.searchMovies(query)
    }

    override suspend fun saveMovies(movies: List<MovieEntity>) {
        movieDao.insertMovies(movies)
    }

    override suspend fun clearMovies() {
        movieDao.clearMovies()
    }
}