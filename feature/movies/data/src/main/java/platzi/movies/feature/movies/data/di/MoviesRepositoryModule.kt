package platzi.movies.feature.movies.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import platzi.movies.feature.movies.data.local.impl.MovieLocalDataSourceImpl
import platzi.movies.feature.movies.data.local.source.MovieLocalDataSource
import platzi.movies.feature.movies.data.remote.impl.MovieRemoteDataSourceImpl
import platzi.movies.feature.movies.data.remote.source.MovieRemoteDataSource
import platzi.movies.feature.movies.data.repository.MovieRepositoryImpl
import platzi.movies.feature.movies.domain.repository.MovieRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        repositoryImpl: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun bindMovieRemoteDataSource(
        remoteDataSourceImpl: MovieRemoteDataSourceImpl
    ): MovieRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindMovieLocalDataSource(
        remoteLocalSourceImpl: MovieLocalDataSourceImpl
    ): MovieLocalDataSource
}