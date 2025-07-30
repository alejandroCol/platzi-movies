package platzi.movies.feature.movies.data.remote.api

import platzi.movies.feature.movies.data.dto.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("discover/movie")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
    ): MovieResponseDto
}