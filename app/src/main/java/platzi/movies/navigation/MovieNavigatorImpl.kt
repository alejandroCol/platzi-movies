package platzi.movies.navigation

import androidx.navigation.NavHostController
import platzi.movies.core.navigation.MovieNavigator
import platzi.movies.core.navigation.Screen

class MovieNavigatorImpl(private val navController: NavHostController) : MovieNavigator {
    override fun openMovieDetail(movieId: Int) {
        navController.navigate(Screen.MovieDetailScreen.createRoute(movieId))
    }
}