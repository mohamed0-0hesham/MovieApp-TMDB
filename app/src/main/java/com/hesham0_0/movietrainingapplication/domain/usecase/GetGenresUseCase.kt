package com.hesham0_0.movietrainingapplication.domain.usecase

import com.hesham0_0.movietrainingapplication.domain.models.Genre
import com.hesham0_0.movietrainingapplication.domain.repository.MovieRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): List<Genre> {
        return repository.getGenres()
    }
}
