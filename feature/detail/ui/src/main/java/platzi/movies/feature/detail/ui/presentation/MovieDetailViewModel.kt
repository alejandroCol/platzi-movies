package platzi.movies.feature.detail.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import platzi.movies.feature.detail.domain.usecases.GetMovieDetailUseCase
import platzi.movies.feature.detail.ui.presentation.state.MovieDetailState
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {

    private val _movieDetail = MutableStateFlow<MovieDetailState>(MovieDetailState.Loading)
    val movieDetail: StateFlow<MovieDetailState> = _movieDetail

    fun loadMovieDetail(movieId: Int) {
        viewModelScope.launch {
            getMovieDetailUseCase(movieId)
                .onSuccess { movie -> _movieDetail.value = MovieDetailState.Success(movie) }
                .onFailure { error -> _movieDetail.value = MovieDetailState.Error(error.message ?: "Unknown error") }
        }
    }
}