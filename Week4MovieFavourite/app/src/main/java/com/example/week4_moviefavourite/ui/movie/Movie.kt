package com.example.week3_movielist

import com.google.gson.annotations.SerializedName

data class ListMovie (
    @SerializedName("results")
    var movie: ArrayList<Movie>
    ) {
    class Movie (
        val id: Int,

        val popularity: Float,
        val vote_count: Int,
        val vote_average: Float,

        val poster_path: String,
        val backdrop_path: String,

        val title: String,
        val original_title: String,

        val adult: Boolean,
        val original_language: String,
        val genre_ids: List<Int>,
        val overview: String,
        val release_date: String
    )
}
