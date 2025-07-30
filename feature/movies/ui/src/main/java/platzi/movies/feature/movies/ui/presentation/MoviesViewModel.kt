package platzi.movies.feature.movies.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import platzi.movies.feature.movies.domain.models.Movie
import platzi.movies.feature.movies.domain.models.MovieQuery
import platzi.movies.feature.movies.domain.usecases.GetPopularMoviesUseCase
import platzi.movies.feature.movies.domain.usecases.SearchMoviesUseCase
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _movies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val movies: StateFlow<PagingData<Movie>> = _movies

    private val _movieQuery = MutableStateFlow(MovieQuery(page = 1, language = "en", query = ""))
    val movieQuery: StateFlow<MovieQuery> = _movieQuery

    init {
        fetchMovies()
    }

    fun setLanguage(language: String) {
        _movieQuery.value = _movieQuery.value.copy(language = language)
        fetchMovies()
    }

    fun onSearchMovies(query: String) {
        _movieQuery.value = _movieQuery.value.copy(query = query)
        fetchMovies()
    }


    private fun getMovies(query: MovieQuery): Flow<PagingData<Movie>> {
        return if (query.query.isNotBlank()) {
            searchMoviesUseCase(query)
        } else {
            getPopularMoviesUseCase(query)
        }
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            getMovies(movieQuery.value)
                .cachedIn(viewModelScope)
                .collect { _movies.value = it }
        }
    }
}