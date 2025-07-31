package platzi.movies.feature.movies.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import platzi.movies.core.navigation.MovieNavigator
import platzi.movies.core.navigation.Screen
import platzi.movies.feature.movies.ui.presentation.MoviesViewModel
import platzi.movies.feature.movies.ui.view.MoviesScreen

fun NavGraphBuilder.moviesNavGraph(
    movieNavigator: MovieNavigator
) {
    composable(Screen.MoviesList.route) {
        val viewModel = hiltViewModel<MoviesViewModel>()
        MoviesScreen(viewModel = viewModel, movieNavigator = movieNavigator)
    }
}