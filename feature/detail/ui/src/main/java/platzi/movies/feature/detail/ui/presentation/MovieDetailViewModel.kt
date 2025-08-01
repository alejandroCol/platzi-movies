package platzi.movies.feature.detail.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import platzi.movies.feature.detail.domain.usecases.GetMovieDetailUseCase
import platzi.movies.feature.detail.domain.usecases.GetMovieTrailerUseCase
import platzi.movies.feature.detail.ui.presentation.state.MovieDetailState
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieTrailerUseCase: GetMovieTrailerUseCase
) : ViewModel() {

    private val _movieDetail = MutableStateFlow<MovieDetailState>(MovieDetailState.Loading)
    val movieDetail: StateFlow<MovieDetailState> = _movieDetail

    fun loadMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _movieDetail.value = MovieDetailState.Loading

            val detailResult = getMovieDetailUseCase(movieId)
            val trailerResult = getMovieTrailerUseCase(movieId)

            when {
                detailResult.isSuccess && trailerResult.isSuccess -> {
                    val movie = detailResult.getOrNull()
                    val trailer = trailerResult.getOrNull()
                    if (movie != null && trailer != null) {
                        _movieDetail.value = MovieDetailState.Success(movie, trailer)
                    } else {
                        _movieDetail.value = MovieDetailState.Error("Error loading movie")
                    }
                }

                detailResult.isFailure -> {
                    val message = detailResult.exceptionOrNull()?.message ?: "Error loading movie"
                    _movieDetail.value = MovieDetailState.Error(message)
                }

                trailerResult.isFailure -> {
                    val message =
                        trailerResult.exceptionOrNull()?.message ?: "Error loading trailer"
                    _movieDetail.value = MovieDetailState.Error(message)
                }
            }
        }
    }
}