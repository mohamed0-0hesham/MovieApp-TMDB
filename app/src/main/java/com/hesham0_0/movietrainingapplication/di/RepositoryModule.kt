package com.hesham0_0.movietrainingapplication.di

import com.hesham0_0.movietrainingapplication.data.repository.MovieRepositoryImpl
import com.hesham0_0.movietrainingapplication.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository
}
