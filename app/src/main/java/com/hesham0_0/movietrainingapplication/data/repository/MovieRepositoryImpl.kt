package com.hesham0_0.movietrainingapplication.data.repository

import android.app.Application
import com.hesham0_0.movietrainingapplication.data.remote.apiServices.*
import com.hesham0_0.movietrainingapplication.domain.models.Genre
import com.hesham0_0.movietrainingapplication.domain.models.MovieList
import com.hesham0_0.movietrainingapplication.domain.repository.MovieRepository
import com.hesham0_0.movietrainingapplication.methods.MethodsClass.Companion.checkForInternet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val context: Application,
    private val trendingApi: TrendingInterface,
    private val genreApi: GenresInterface,
    private val searchApi: SearchInterface,
    private val topRatedApi: TopRatedInterface,
    private val upcomingApi: UpcomingInterface,
    private val popularApi: PopularInterface
) : MovieRepository {

    override suspend fun getTrendingMovies(mediaType: String, timeWindow: String, page: Int): MovieList {
        return if (checkForInternet(context)) {
            val response = withContext(Dispatchers.IO) { trendingApi.getData(mediaType, timeWindow, page.toString()) }
            if (response.isSuccessful)
                response.body()
                    ?: MovieList(0, 0, emptyList(), 0, 0)
            else MovieList(0, 0, emptyList(), 0, 0)
        } else MovieList(0, 0, emptyList(), 0, 0)
    }

    override suspend fun getGenres(): List<Genre> {
        return if (checkForInternet(context)) {
            val response = withContext(Dispatchers.IO) { genreApi.getGenres() }
            if (response.isSuccessful)
                response.body()?.genres
                    ?: emptyList()
            else emptyList()
        } else emptyList()
    }

    override suspend fun searchMovies(query: String, page: Int): MovieList {
        return if (checkForInternet(context)) {
            val response = withContext(Dispatchers.IO) { searchApi.getSearchResult(query, page.toString()) }
            if (response.isSuccessful) response.body() ?: MovieList(0, 0, emptyList(), 0, 0)
            else MovieList(0, 0, emptyList(), 0, 0)
        } else MovieList(0, 0, emptyList(), 0, 0)
    }

    override suspend fun getTopRatedMovies(page: Int): MovieList {
        return if (checkForInternet(context)) {
            val response = withContext(Dispatchers.IO) { topRatedApi.getTopRatedList(page.toString()) }
            if (response.isSuccessful) response.body() ?: MovieList(0, 0, emptyList(), 0, 0)
            else MovieList(0, 0, emptyList(), 0, 0)
        } else MovieList(0, 0, emptyList(), 0, 0)
    }

    override suspend fun getUpcomingMovies(page: Int): MovieList {
        return if (checkForInternet(context)) {
            val response = withContext(Dispatchers.IO) { upcomingApi.getUpcomingList(page.toString()) }
            if (response.isSuccessful) response.body() ?: MovieList(0, 0, emptyList(), 0, 0)
            else MovieList(0, 0, emptyList(), 0, 0)
        } else MovieList(0, 0, emptyList(), 0, 0)
    }

    override suspend fun getPopularMovies(page: Int): MovieList {
        return if (checkForInternet(context)) {
            val response = withContext(Dispatchers.IO) { popularApi.getPopularList(page.toString()) }
            if (response.isSuccessful) response.body() ?: MovieList(0, 0, emptyList(), 0, 0)
            else MovieList(0, 0, emptyList(), 0, 0)
        } else MovieList(0, 0, emptyList(), 0, 0)
    }
}
