package com.hesham0_0.movietrainingapplication.data.remote.apiServices

import com.hesham0_0.movietrainingapplication.domain.models.GenresList
import retrofit2.Response
import retrofit2.http.GET

interface GenresInterface {
    @GET("genre/movie/list?api_key=b3563f4400d79d42e38fd6f89f603e6c&language=en-US")
    suspend fun getGenres(): Response<GenresList>
}