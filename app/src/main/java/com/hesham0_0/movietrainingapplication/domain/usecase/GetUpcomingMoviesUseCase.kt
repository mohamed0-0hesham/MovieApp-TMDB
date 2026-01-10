package com.hesham0_0.movietrainingapplication.domain.usecase

import com.hesham0_0.movietrainingapplication.domain.models.MovieList
import com.hesham0_0.movietrainingapplication.domain.repository.MovieRepository
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int): MovieList {
        return repository.getUpcomingMovies(page)
    }
}
