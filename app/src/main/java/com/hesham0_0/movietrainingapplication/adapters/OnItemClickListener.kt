package com.hesham0_0.movietrainingapplication.adapters

import com.hesham0_0.movietrainingapplication.models.MovieItem


interface OnItemClickListener {
    fun onItemClick(position: Int, movieItem: MovieItem)
}