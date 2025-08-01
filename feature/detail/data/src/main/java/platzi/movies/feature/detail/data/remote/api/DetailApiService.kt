package platzi.movies.feature.detail.data.remote.api

import platzi.movies.feature.detail.data.dto.MovieDetailDto
import platzi.movies.feature.detail.data.dto.ResponseTrailerDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailApiService {
    @GET("movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId") movieId: Int,
        @Query("language") language: String = "en-US"
    ): MovieDetailDto

    @GET("movie/{movieId}/videos")
    suspend fun getMovieTrailer(
        @Path("movieId") movieId: Int,
        @Query("language") language: String = "en-US"
    ): ResponseTrailerDto
}