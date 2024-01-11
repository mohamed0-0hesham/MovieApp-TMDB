package com.hesham0_0.movietrainingapplication.models

import androidx.room.Entity
import androidx.room.PrimaryKey


data class MovieList(
    val Id:Int?=null,
    val page:Int,
    var results: List<MovieItem>,
    val total_pages: Int,
    val total_results: Int
)
@Entity(tableName = "Movie_table")
data class MovieItem(
    @PrimaryKey(autoGenerate = true)
    val Id:Int?=null,
    val adult: Boolean?,
    val backdrop_path: String?,
    val first_air_date: String?,
    val genre_ids: List<String>?,
    val media_type: String?,
    val name: String?,
    val original_language: String?,
    val original_name: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
)