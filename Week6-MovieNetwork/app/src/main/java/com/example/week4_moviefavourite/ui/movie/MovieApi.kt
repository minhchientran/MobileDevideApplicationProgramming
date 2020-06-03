package com.example.week4_moviefavourite.ui.movie

import retrofit2.Call
import retrofit2.http.GET

interface MovieApi {
    @GET("movie/top_rated")
    fun getTopRating() : Call<ListMovie>

    @GET("movie/now_playing")
    fun getNowPlaying(): Call<ListMovie>
}