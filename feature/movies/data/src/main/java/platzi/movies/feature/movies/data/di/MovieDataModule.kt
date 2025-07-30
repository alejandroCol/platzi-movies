package platzi.movies.feature.movies.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import platzi.movies.feature.movies.data.remote.source.MovieRemoteDataSource
import platzi.movies.feature.movies.data.remote.api.MovieApiService
import platzi.movies.feature.movies.data.remote.impl.MovieRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDataModule {

    @Provides
    fun provideMovieRemoteDataSource(
        movieApiService: MovieApiService
    ): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(movieApiService)
    }

    @Binds
    @Singleton
    abstract fun bindMovieRemoteDataSource(
        remoteDataSourceImpl: MovieRemoteDataSourceImpl
    ): MovieRemoteDataSource
}