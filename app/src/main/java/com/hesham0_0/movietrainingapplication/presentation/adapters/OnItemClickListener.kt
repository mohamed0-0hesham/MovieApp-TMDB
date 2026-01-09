package com.hesham0_0.movietrainingapplication.presentation.adapters

import com.hesham0_0.movietrainingapplication.domain.models.MovieItem


interface OnItemClickListener {
    fun onItemClick(position: Int, movieItem: MovieItem)
}