package com.hesham0_0.movietrainingapplication.ui.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.hesham0_0.movietrainingapplication.data.ProductRepository
import com.hesham0_0.movietrainingapplication.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class SharedViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {

    val selectedProduct: MutableLiveData<MovieItem> = MutableLiveData()

    val selectedListId: MutableLiveData<Int> = MutableLiveData()

    val trendingValues: MutableLiveData<List<String>> = MutableLiveData()

    val genre: LiveData<List<Genre>> = liveData {
        val data = productRepository.getGenres()
        emit(data)
    }

    val selectedPage: MutableLiveData<Int?> = MutableLiveData()

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
            val data = productRepository.getData(media_type, time_window, page.toString())
            trendingMovieList.value = data.results
            emit(data)
        }

    fun searchResult(): LiveData<String> = liveData {
        val containerList: List<MovieItem>
        val data = productRepository.getSearchResult(
            searchMovie = searchMovieName.value!!,
            page = selectedPageSearch.value.toString()
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
            for (i in 1..selectedGenreId.value!!.size * 10) {
                val data = productRepository.getTopRatedResult(selectedPage.toString())
                if (selectedPage.value!! <= data.total_pages) {
                    selectedPage.value = selectedPage.value?.plus(1)
                    val response =
                        productRepository.getTopRatedResult(selectedPage.value.toString())
                    val genreList = response.results.filter {
                        filterListByList(it.genre_ids!!, selectedGenreId.value!!)
                    }
                    genreFilterList = genreFilterList + genreList
                } else {
                    emit("No More Results")
                }
                if (genreFilterList.isEmpty()){
                    emit("There are No Results")
                }
            }
        } else {
            genreMovieList.value = emptyList()
            emit("Choose Genre")
        }
        genreMovieList.value = genreMovieList.value!! + genreFilterList
        emit("Finish")
    }

    open fun topRatedResult(page: Int): LiveData<MovieList> = liveData {
        val data = productRepository.getTopRatedResult(page.toString())
        topRatedMovieList.value = data.results
        emit(data)
    }

    fun upcomingResult(page: Int): LiveData<MovieList> = liveData {
        val data = productRepository.getUpcomingResult(page.toString())
        upcomingMovieList.value = data.results
        emit(data)
    }

    fun popularResult(page: Int): LiveData<MovieList> = liveData {
        val data = productRepository.getPopularResult(page.toString())

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