package platzi.movies.feature.detail.data.repository

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import platzi.movies.feature.detail.data.remote.source.DetailRemoteDataSource
import platzi.movies.feature.detail.domain.models.MovieDetail

class MovieRepositoryImplTest {

    private lateinit var movieRepositoryImpl: DetailRepositoryImpl
    private val remoteDataSource: DetailRemoteDataSource = mockk()

    @Before
    fun setUp() {
        movieRepositoryImpl = DetailRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `when getMovieDetail then return MovieDetail`() = runTest {
        val movieId = 123
        val movieDetail = MovieDetail(
            id = movieId,
            title = "Avengers",
            overview = "Overview",
            posterUrl = "",
            backdropUrl = null,
            releaseDate = null,
            revenue = null,
            runtime = null,
            rating = null,
            budget = null,
            tagline = null
        )

        coEvery { remoteDataSource.fetchMovieDetail(movieId) } returns movieDetail

        val result = movieRepositoryImpl.getMovieDetail(movieId)

        assertEquals(movieDetail, result)
    }
}