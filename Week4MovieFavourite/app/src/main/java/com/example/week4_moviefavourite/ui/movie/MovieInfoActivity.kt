package com.example.week4_moviefavourite.ui.movie

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Switch
import androidx.core.view.setPadding
import com.bumptech.glide.Glide
import com.example.week3_movielist.ListMovie
import com.example.week4_moviefavourite.R
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import com.khtn.androidcamp.DataCenter
import kotlinx.android.synthetic.main.activity_movie_info.*

class MovieInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_info)

        supportActionBar?.hide()

        val movieInfo = intent.extras?.getString("MOVIE_INFO")
        if (movieInfo != null) {
            val movie = convertNestedJsonStringToObject(movieInfo)
            i_title.text = movie.title
            org_title.text = movie.original_title

            popularity.text = "(${movie.popularity.toString()} users)"
            vote_count.text = "(${movie.vote_count.toString()} votes)"
            vote_average.text = movie.vote_average.toString()

            language.text = convertLanguage(movie.original_language)
            overview.text = movie.overview
            release_date.text = movie.release_date
            adult.isChecked = movie.adult

            ratingBar.rating = movie.vote_average / 2

            for (i in movie.genre_ids.indices) {
                var chip = LayoutInflater.from(this).inflate(R.layout.chip,null) as Chip
                chip.text = convertGenreId(movie.genre_ids[i])
                genre.addView(chip)
            }

            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .fitCenter()
                .into(i_poster)

            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500${movie.backdrop_path}")
                .fitCenter()
                .into(i_backdrop)
        }

    }

    private fun convertNestedJsonStringToObject(movieInfo: String?): ListMovie.Movie{
        return Gson().fromJson(movieInfo, ListMovie.Movie::class.java)
    }

    private fun convertLanguage(lang : String) : String {
        return when (lang) {
            "en" -> "English"
            "ko" -> "Korean"
            "zh" -> "Chinese"
            "ru" -> "Russian"
            "it" -> "Italian"
            else -> ""
        }
    }

    private fun convertGenreId(id: Int) : String {
        return when (id) {
            28 -> "action"
            16 -> "animated"
            99 -> "documentary"
            18 -> "drama"
            10751 -> "family"
            14 -> "fantasy"
            36 -> "history"
            35 -> "comedy"
            10752 -> "war"
            80 -> "crime"
            10402 -> "music"
            9648 -> "mystery"
            10749 -> "romance"
            878 -> "sci-fi"
            27 -> "horror"
            10770 -> "TV movie"
            53 -> "thriller"
            37 -> "western"
            12 -> "adventure"
            else -> ""
        }
    }
}
