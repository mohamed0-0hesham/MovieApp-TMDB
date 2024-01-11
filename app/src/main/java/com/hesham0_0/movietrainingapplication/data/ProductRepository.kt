package com.hesham0_0.movietrainingapplication.data

import android.app.Application
import android.widget.Toast
import com.hesham0_0.movietrainingapplication.data.apiInterfaces.*
import com.hesham0_0.movietrainingapplication.methods.MethodsClass.Companion.checkForInternet
import com.hesham0_0.movietrainingapplication.models.Genre
import com.hesham0_0.movietrainingapplication.models.MovieList
import kotlinx.coroutines.*
import retrofit2.Retrofit
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val context: Application,
    private val trendingApi: TrendingInterface,
    private val genreApi: GenresInterface,
    private val searchApi: SearchInterface,
    private val topRatedApi: TopRatedInterface,
    private val upcomingApi: UpcomingInterface,
    private val popularApi: PopularInterface
) {

    suspend fun getData(media_type: String, time_window: String, page: String): MovieList {
        if (checkForInternet(context)) {
            val response =
                withContext(Dispatchers.IO) { trendingApi.getData(media_type, time_window, page) }
            if (response.isSuccessful && response.body() != null) {
                return response.body()!!
            }
        }
        return MovieList(0, 0, emptyList(), 0, 0)
    }

    suspend fun getGenres(): List<Genre> {
        if (checkForInternet(context)) {
            val response = withContext(Dispatchers.IO) { genreApi.getGenres() }
            if (response.isSuccessful && response.body() != null) {
                return response.body()!!.genres
            }
        }
        return emptyList()
    }


    suspend fun getSearchResult(searchMovie: String, page: String): MovieList {
        if (checkForInternet(context)) {
            try {
                val response =
                    withContext(Dispatchers.IO) { searchApi.getSearchResult(searchMovie, page) }
                if (response.isSuccessful && response.body() != null) {
                    return response.body()!!
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
            }
        }
        return MovieList(0, 0, emptyList(), 0, 0)
    }

    suspend fun getTopRatedResult(page: String): MovieList {
        if (checkForInternet(context)) {
            try {
                val response = withContext(Dispatchers.IO) { topRatedApi.getTopRatedList(page) }
                if (response.isSuccessful && response.body() != null) {
                    return response.body()!!
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
            }
        }
        return MovieList(0, 0, emptyList(), 0, 0)
    }

    suspend fun getUpcomingResult(page: String): MovieList {
        if (checkForInternet(context)) {
            try {
                val response =
                    withContext(Dispatchers.IO) { upcomingApi.getUpcomingList(page) }
                if (response.isSuccessful && response.body() != null) {
                    return response.body()!!
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
            }
        }
        return MovieList(0, 0, emptyList(), 0, 0)
    }

    suspend fun getPopularResult(page: String): MovieList {
        if (checkForInternet(context)) {
            try {
                val response =
                    withContext(Dispatchers.IO) { popularApi.getPopularList(page) }
                if (response.isSuccessful && response.body() != null) {
                    return response.body()!!
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
            }
        }
        return MovieList(0, 0, emptyList(), 0, 0)
    }
}



