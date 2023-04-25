package com.example.movietrainingapplication.models

data class GenresList(
    val genres: List<Genre>
)
data class Genre(
    val Id:Int?=null,
    val id: String,
    val name: String
)