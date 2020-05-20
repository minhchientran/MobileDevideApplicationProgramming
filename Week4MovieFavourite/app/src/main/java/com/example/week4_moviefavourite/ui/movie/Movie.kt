package com.example.week4_moviefavourite.ui.movie

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.khtn.androidcamp.DataCenter

var listMovie = convertNestedJsonStringToObject()

fun convertNestedJsonStringToObject() : ListMovie {
    return Gson().fromJson(DataCenter.getMovieJsonString(), ListMovie::class.java)
}

fun convertNestedObjectToJsonString(movie : ListMovie.Movie): String {
    return Gson().toJson(movie)
}

fun convertNestedJsonStringToObject(movieInfo: String?): ListMovie.Movie {
    return Gson().fromJson(movieInfo, ListMovie.Movie::class.java)
}

fun cloneListMovie(list : ListMovie) : ListMovie {
    val jsonString = Gson().toJson(list)
    return Gson().fromJson(jsonString, ListMovie::class.java)
}

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
        val release_date: String,

        var favourite : Boolean = false
    )
}
