package platzi.movies.feature.detail.ui.presentation

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import platzi.movies.feature.detail.domain.models.MovieDetail
import platzi.movies.feature.detail.domain.models.MovieTrailer
import platzi.movies.feature.detail.domain.usecases.GetMovieDetailUseCase
import platzi.movies.feature.detail.domain.usecases.GetMovieTrailerUseCase
import platzi.movies.feature.detail.ui.presentation.state.MovieDetailState

@ExperimentalCoroutinesApi
class MovieDetailViewModelTest {

    private lateinit var viewModel: MovieDetailViewModel
    private val getMovieDetailUseCase: GetMovieDetailUseCase = mockk()
    private val getMovieTrailerUseCase: GetMovieTrailerUseCase = mockk()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        viewModel = MovieDetailViewModel(
            getMovieDetailUseCase,
            getMovieTrailerUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when loadMovieDetail then should update movieDetail to Success`() = runTest {
        val movieId = 1
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
        val movieTrailer = MovieTrailer(
            videoUrl = "url",
            videoId = "1",
            isOfficial = true
        )
        coEvery { getMovieDetailUseCase(movieId) } returns Result.success(movieDetail)
        coEvery { getMovieTrailerUseCase(movieId) } returns Result.success(movieTrailer)

        viewModel.movieDetail.test {
            assertEquals(MovieDetailState.Loading, awaitItem())

            viewModel.loadMovieDetail(movieId)

            assertEquals(MovieDetailState.Success(movieDetail, movieTrailer), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when loadMovieDetail then should update movieDetail to Error `() = runTest {
        val movieId = 1
        val errorMessage = "Error fetching movie detail"
        coEvery { getMovieDetailUseCase(movieId) } returns Result.failure(Exception(errorMessage))
        coEvery { getMovieTrailerUseCase(movieId) } returns Result.failure(Exception(errorMessage))

        viewModel.movieDetail.test {
            assertEquals(MovieDetailState.Loading, awaitItem())

            viewModel.loadMovieDetail(movieId)

            assertEquals(MovieDetailState.Error(errorMessage), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}