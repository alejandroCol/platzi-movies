package platzi.movies.feature.detail.ui.presentation.state

import platzi.movies.feature.detail.domain.models.MovieDetail
import platzi.movies.feature.detail.domain.models.MovieTrailer

sealed class MovieDetailState {
    object Loading : MovieDetailState()
    data class Success(
        val movie: MovieDetail,
        val trailer: MovieTrailer
    ) : MovieDetailState()

    data class Error(val message: String) : MovieDetailState()
}