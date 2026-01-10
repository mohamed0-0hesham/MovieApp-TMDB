package com.hesham0_0.movietrainingapplication.domain.repository

import com.hesham0_0.movietrainingapplication.domain.models.Genre
import com.hesham0_0.movietrainingapplication.domain.models.MovieList

interface MovieRepository {
    suspend fun getTrendingMovies(mediaType: String, timeWindow: String, page: Int): MovieList
    suspend fun getGenres(): List<Genre>
    suspend fun searchMovies(query: String, page: Int): MovieList
    suspend fun getTopRatedMovies(page: Int): MovieList
    suspend fun getUpcomingMovies(page: Int): MovieList
    suspend fun getPopularMovies(page: Int): MovieList
}
