package platzi.movies.feature.detail.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import platzi.movies.feature.detail.ui.view.MovieDetailScreen
import androidx.navigation.compose.composable

const val MOVIE_ID_ARG = "movieId"
const val movieDetailRoute = "movie_detail_screen/{$MOVIE_ID_ARG}"

fun NavGraphBuilder.movieDetailNavGraph(
    navController: NavController
) {
    composable(
        route = movieDetailRoute,
        arguments = listOf(navArgument(MOVIE_ID_ARG) { type = NavType.IntType })
    ) { backStackEntry ->
        val movieId = backStackEntry.arguments?.getInt(MOVIE_ID_ARG) ?: return@composable
        MovieDetailScreen(movieId = movieId, navController = navController)
    }
}