package com.example.week4_moviefavourite.ui.movie

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.khtn.androidcamp.DataCenter

var listNowPlayingMovie = getNowPlayingMovie()
var listTopRatingMovie = getTopRateMovie()
var listFavouriteMovie = ListMovie(ArrayList())

fun getNowPlayingMovie() : ListMovie {
    return Gson().fromJson(DataCenter.getNowPlayingMovieJson(), ListMovie::class.java)
}

fun getTopRateMovie() : ListMovie {
    return Gson().fromJson(DataCenter.getTopRateMovieJson(), ListMovie::class.java)
}

fun convertNestedObjectToJsonString(movie : ListMovie.Movie): String {
    return Gson().toJson(movie)
}

fun convertNestedJsonStringToObject(movieInfo: String?): ListMovie.Movie {
    return Gson().fromJson(movieInfo, ListMovie.Movie::class.java)
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
