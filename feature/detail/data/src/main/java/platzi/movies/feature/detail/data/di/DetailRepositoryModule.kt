package platzi.movies.feature.detail.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import platzi.movies.feature.detail.data.remote.impl.DetailRemoteDataSourceImpl
import platzi.movies.feature.detail.data.remote.source.DetailRemoteDataSource
import platzi.movies.feature.detail.data.repository.DetailRepositoryImpl
import platzi.movies.feature.detail.domain.repository.DetailRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DetailRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDetailRepository(
        repositoryImpl: DetailRepositoryImpl
    ): DetailRepository

    @Binds
    @Singleton
    abstract fun bindDetailRemoteDataSource(
        remoteDataSourceImpl: DetailRemoteDataSourceImpl
    ): DetailRemoteDataSource
}