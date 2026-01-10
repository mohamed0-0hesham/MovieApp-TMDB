package com.hesham0_0.movietrainingapplication.presentation.viewModel

import androidx.lifecycle.*
import com.hesham0_0.movietrainingapplication.domain.repository.MovieRepository // Updated Import
import com.hesham0_0.movietrainingapplication.domain.models.Genre
import com.hesham0_0.movietrainingapplication.domain.models.MovieItem
import com.hesham0_0.movietrainingapplication.domain.models.MovieList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class SharedViewModel @Inject constructor(
    private val movieRepository: MovieRepository // Updated constructor parameter
) : ViewModel() {

    val selectedProduct: MutableLiveData<MovieItem> = MutableLiveData()
    val selectedListId: MutableLiveData<Int> = MutableLiveData()
    val trendingValues: MutableLiveData<List<String>> = MutableLiveData()

    val genre: LiveData<List<Genre>> = liveData {
        // Changed productRepository to movieRepository
        val data = movieRepository.getGenres()
        emit(data)
    }

    val selectedPage: MutableLiveData<Int> = MutableLiveData()
    val selectedGenreId: MutableLiveData<List<Int>> = MutableLiveData()
    var genreMovieList: MutableLiveData<List<MovieItem>> = MutableLiveData()
    var trendingMovieList: MutableLiveData<List<MovieItem>> = MutableLiveData()
    var topRatedMovieList: MutableLiveData<List<MovieItem>> = MutableLiveData()
    var upcomingMovieList: MutableLiveData<List<MovieItem>> = MutableLiveData()
    var popularMovieList: MutableLiveData<List<MovieItem>> = MutableLiveData()

    val selectedPageSearch: MutableLiveData<Int?> = MutableLiveData()
    var searchMovieList: MutableLiveData<List<MovieItem>> = MutableLiveData()
    val searchMovieName: MutableLiveData<String> = MutableLiveData()

    fun trendingResult(media_type: String, time_window: String, page: Int): LiveData<MovieList> =
        liveData {
            // Changed productRepository to movieRepository
            val data = movieRepository.getTrendingMovies(media_type, time_window, page)
            trendingMovieList.value = data.results
            emit(data)
        }

    fun searchResult(): LiveData<String> = liveData {
        val containerList: List<MovieItem>
        // Changed productRepository to movieRepository
        val data = movieRepository.searchMovies(
            query = searchMovieName.value!!,
            page = selectedPageSearch.value!!
        )
        if (data.total_pages == 0) {
            emit("No Data")
        } else if (selectedPageSearch.value!! <= data.total_pages) {
            selectedPageSearch.value = selectedPageSearch.value?.plus(1)
            containerList = data.results
            searchMovieList.value = searchMovieList.value!! + containerList
            emit("Successful")
        } else {
            emit("No More Results")
        }
    }

    fun getGenreMovieList(): LiveData<String> = liveData {
        var genreFilterList: List<MovieItem> = emptyList()
        if (selectedGenreId.value!!.isNotEmpty()) {
            // Changed productRepository to movieRepository
            val data = movieRepository.getTopRatedMovies(selectedPage.value!!)
            while (genreFilterList.size < 20 && selectedPage.value!! <= data.total_pages) {
                if (selectedPage.value!! <= data.total_pages) {
                    selectedPage.value = selectedPage.value?.plus(1)
                    val response = movieRepository.getTopRatedMovies(selectedPage.value!!)
                    val genreList = response.results.filter {
                        filterListByList(it.genre_ids!!, selectedGenreId.value!!)
                    }
                    genreFilterList = genreFilterList + genreList
                }
            }
            if (genreFilterList.isEmpty()) {
                emit("There are No Results")
            }

        } else {
            genreMovieList.value = emptyList()
            emit("Choose Genre")
        }
        genreMovieList.value = genreMovieList.value!! + genreFilterList
        emit("Finish")
    }

    open fun topRatedResult(page: Int): LiveData<MovieList> = liveData {
        // Changed productRepository to movieRepository
        val data = movieRepository.getTopRatedMovies(page)
        topRatedMovieList.value = data.results
        emit(data)
    }

    fun upcomingResult(page: Int): LiveData<MovieList> = liveData {
        // Changed productRepository to movieRepository
        val data = movieRepository.getUpcomingMovies(page)
        upcomingMovieList.value = data.results
        emit(data)
    }

    fun popularResult(page: Int): LiveData<MovieList> = liveData {
        // Changed productRepository to movieRepository
        val data = movieRepository.getPopularMovies(page)
        popularMovieList.value = data.results
        emit(data)
    }

    private fun filterListByList(list: List<String>, filterList: List<Int>): Boolean {
        for (i in filterList) {
            val x = i.toString()
            if (x in list) {
                continue
            } else {
                return false
            }
        }
        return true
    }
}
