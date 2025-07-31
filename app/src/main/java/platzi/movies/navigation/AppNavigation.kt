package platzi.movies.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import platzi.movies.core.navigation.Screen
import platzi.movies.feature.movies.ui.navigation.moviesNavGraph

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val movieNavigator = remember(navController) {
        MovieNavigatorImpl(navController)
    }

    NavHost(
        navController = navController,
        startDestination = Screen.MoviesList.route
    ) {
        moviesNavGraph(movieNavigator = movieNavigator)
    }
}