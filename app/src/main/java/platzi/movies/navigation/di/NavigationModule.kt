package platzi.movies.navigation.di

import androidx.navigation.NavHostController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import platzi.movies.core.navigation.MovieNavigator
import platzi.movies.navigation.MovieNavigatorImpl

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    fun provideMovieNavigator(navController: NavHostController): MovieNavigator {
        return MovieNavigatorImpl(navController)
    }
}