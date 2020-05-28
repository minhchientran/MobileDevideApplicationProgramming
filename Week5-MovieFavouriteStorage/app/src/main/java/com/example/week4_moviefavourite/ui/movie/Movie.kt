package com.example.week4_moviefavourite.ui.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
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
    @Entity(tableName = "movie_table")
    @TypeConverters(Converters::class)
    class Movie (

        @PrimaryKey(autoGenerate = false) val id: Int,

        val popularity: Float,
        val vote_count: Int,
        val vote_average: Float,

        val poster_path: String,
        val backdrop_path: String,
        val title: String,
        val original_title: String,
        val overview: String,
        val release_date: String,
        val original_language: String,

        val adult: Boolean,
        var favourite : Boolean = false,

        val genre_ids: List<Int>
    )
}

class Converters {
    @TypeConverter
    fun listToJson(value: List<Int>?) = Gson().toJson(value)!!

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Int>::class.java).toList()
}
