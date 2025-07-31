package platzi.movies.feature.detail.ui.presentation.state

import platzi.movies.feature.detail.domain.models.MovieDetail

sealed class MovieDetailState {
    object Loading : MovieDetailState()
    data class Success(val movie: MovieDetail) : MovieDetailState()
    data class Error(val message: String) : MovieDetailState()
}