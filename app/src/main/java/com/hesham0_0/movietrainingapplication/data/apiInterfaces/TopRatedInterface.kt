package com.hesham0_0.movietrainingapplication.data.apiInterfaces

import com.hesham0_0.movietrainingapplication.models.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TopRatedInterface {
    @GET("movie/top_rated?api_key=93a203e39bf33f4f09b0dffd4d9015ec")
    suspend fun getTopRatedList(
        @Query("page") page: String,
    ): Response<MovieList>
}
interface UpcomingInterface {
    @GET("movie/upcoming?api_key=93a203e39bf33f4f09b0dffd4d9015ec")
    suspend fun getUpcomingList(
        @Query("page") page: String,
    ): Response<MovieList>
}
interface PopularInterface {
    @GET("movie/popular?api_key=93a203e39bf33f4f09b0dffd4d9015ec")
    suspend fun getPopularList(
        @Query("page") page: String,
    ): Response<MovieList>
}