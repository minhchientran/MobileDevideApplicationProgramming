package com.example.week4_moviefavourite.ui.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.week4_moviefavourite.MainActivity
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var listNowPlayingMovie = ListMovie(ArrayList(), 0)
var listTopRatingMovie = ListMovie(ArrayList(), 0)
var listFavouriteMovie = ListMovie(ArrayList(), 0)

fun convertNestedObjectToJsonString(movie : ListMovie.Movie): String {
    return Gson().toJson(movie)
}

fun convertNestedJsonStringToObject(movieInfo: String?): ListMovie.Movie {
    return Gson().fromJson(movieInfo, ListMovie.Movie::class.java)
}

fun getNowPlayingFromApi(func : (() ->  Unit)?, page : Int) {
    MovieService.getInstance().getApi().getNowPlaying(page).enqueue(object : Callback<ListMovie> {
        override fun onFailure(call: Call<ListMovie>?, t: Throwable?) {
        }
        override fun onResponse(call: Call<ListMovie>?, response: Response<ListMovie>?) {
            response?.let {
                val resp = it.body()
                listNowPlayingMovie.movie.addAll(resp.movie)
                if (page == 1) listNowPlayingMovie.totalPages = resp.totalPages
            }
            if (func != null) {
                func()
            }
        }
    })
}

fun getTopRatingFromApi(func : (() ->  Unit)?, page : Int) {
    MovieService.getInstance().getApi().getTopRating(page).enqueue(object : Callback<ListMovie> {
        override fun onFailure(call: Call<ListMovie>?, t: Throwable?) {
        }
        override fun onResponse(call: Call<ListMovie>?, response: Response<ListMovie>?) {
            response?.let {
                val resp = it.body()
                listTopRatingMovie.movie.addAll(resp.movie)
                if (page == 1) listTopRatingMovie.totalPages = resp.totalPages
            }
            if (func != null) {
                func()
            }
        }
    })
}

data class ListMovie (
    @SerializedName("results") var movie: ArrayList<Movie>,
    @SerializedName("total_pages") var totalPages : Int
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
