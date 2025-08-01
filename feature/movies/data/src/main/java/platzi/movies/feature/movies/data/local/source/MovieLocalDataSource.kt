package platzi.movies.feature.movies.data.local.source

import platzi.movies.feature.movies.data.local.entity.MovieEntity

interface MovieLocalDataSource {
    suspend fun getCachedMovies(): List<MovieEntity>
    suspend fun searchMovies(query: String): List<MovieEntity>
    suspend fun saveMovies(movies: List<MovieEntity>)
    suspend fun clearMovies()
}