package com.hesham0_0.movietrainingapplication.di

import com.hesham0_0.movietrainingapplication.data.apiInterfaces.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun retrofitProvider(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun trendingApi(): TrendingInterface {
        return retrofitProvider().create(TrendingInterface::class.java)
    }

    @Provides
    @Singleton
    fun genresApi(): GenresInterface {
        return retrofitProvider().create(GenresInterface::class.java)
    }

    @Provides
    @Singleton
    fun searchApi(): SearchInterface {
        return retrofitProvider().create(SearchInterface::class.java)
    }

    @Provides
    @Singleton
    fun topRatedApi(): TopRatedInterface {
        return retrofitProvider().create(TopRatedInterface::class.java)
    }

    @Provides
    @Singleton
    fun upcomingApi(): UpcomingInterface {
        return retrofitProvider().create(UpcomingInterface::class.java)
    }

    @Provides
    @Singleton
    fun popularApi(): PopularInterface {
        return retrofitProvider().create(PopularInterface::class.java)
    }
}