package com.hesham0_0.movietrainingapplication.data.remote.apiServices

import com.hesham0_0.movietrainingapplication.domain.models.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrendingInterface {
    @GET("trending/{media_type}/{time_window}?api_key=93a203e39bf33f4f09b0dffd4d9015ec")
    suspend fun getData(
        @Path("media_type") media_type: String,
        @Path("time_window") time_window: String,
        @Query("page") page: String,
    ): Response<MovieList>
}