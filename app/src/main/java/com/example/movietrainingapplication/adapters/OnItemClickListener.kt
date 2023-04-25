package com.example.movietrainingapplication.adapters

import com.example.movietrainingapplication.models.MovieItem

interface OnItemClickListener {
    fun onItemClick(position: Int, movieItem: MovieItem)
}