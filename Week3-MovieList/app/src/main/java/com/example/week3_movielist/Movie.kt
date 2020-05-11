package com.example.week3_movielist

import com.google.gson.annotations.SerializedName

data class ListMovie (
    @SerializedName("results")
    val movie: ArrayList<Movie>
    ) {
    class Movie (
        val popularity: Float,
        val vote_count: Int,
        val poster_path: String,
        val id: Int,
        val adult: Boolean,
        val backdrop_path: String,
        val original_language: String,
        val original_title: String,
        val genre_ids: List<Int>,
        val title: String,
        val vote_average: Float,
        val overview: String,
        val release_date: String
    )
}
