package com.example.week4_moviefavourite.ui.movie

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/top_rated")
    fun getTopRating(@Query("page")page : Int) : Call<ListMovie>

    @GET("movie/now_playing")
    fun getNowPlaying(@Query("page")page : Int): Call<ListMovie>
}