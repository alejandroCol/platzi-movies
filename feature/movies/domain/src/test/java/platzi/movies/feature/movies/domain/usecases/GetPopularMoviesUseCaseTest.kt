package platzi.movies.feature.movies.domain.usecases


import androidx.paging.PagingData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import io.mockk.verify
import org.junit.Assert.assertEquals
import platzi.movies.feature.movies.domain.models.Movie
import platzi.movies.feature.movies.domain.models.MovieQuery
import platzi.movies.feature.movies.domain.repository.MovieRepository

class GetPopularMoviesUseCaseTest {

    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase
    private val repository: MovieRepository = mockk()

    @Before
    fun setUp() {
        getPopularMoviesUseCase = GetPopularMoviesUseCase(repository)
    }

    @Test
    fun `when getPopularMovies then return Flow of PagingData`() = runTest {
        val query = MovieQuery(1,"ES")
        val pagingData: PagingData<Movie> = mockk()

        coEvery { repository.getPopularMovies(query) } returns flowOf(pagingData)

        val result = getPopularMoviesUseCase(query).first()

        assertEquals(pagingData, result)
        verify { repository.getPopularMovies(query) }
    }

    @Test
    fun `when should return empty flow when repository returns empty Flow`() = runTest {
        val query = MovieQuery(1,"ES")
        val emptyPagingData: PagingData<Movie> = PagingData.empty()

        coEvery { repository.getPopularMovies(query) } returns flowOf(emptyPagingData)

        val result = getPopularMoviesUseCase(query).first()

        assertEquals(emptyPagingData, result)
    }
}