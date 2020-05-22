package com.example.week4_moviefavourite

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.week4_moviefavourite.ui.movie.ListMovie
import com.example.week4_moviefavourite.ui.movie.convertNestedJsonStringToObject
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_movie_info.*

class MovieInfoActivity : AppCompatActivity() {

    private var lastStatus = false
    private var movie : ListMovie.Movie? = null
    private var frag : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_info)
        supportActionBar?.hide()

        val movieInfo = intent.extras?.getString("MOVIE_INFO")
        frag = intent.extras?.getString("Fragment")
        if (movieInfo != null) {
            movie =
                convertNestedJsonStringToObject(
                    movieInfo
                )
            i_title.text = movie?.title
            org_title.text = movie?.original_title
            popularity.text = "(${movie?.popularity.toString()} users)"
            vote_count.text = "(${movie?.vote_count.toString()} votes)"
            vote_average.text = movie?.vote_average.toString()
            language.text = movie?.original_language?.let { convertLanguage(it) }
            overview.text = movie?.overview
            release_date.text = movie?.release_date
            adult.isChecked = movie?.adult!!
            ratingBar.rating = movie?.vote_average?.div(2) as Float

            for (i in movie!!.genre_ids.indices) {
                val chip =
                    LayoutInflater.from(this).inflate(R.layout.chip,null) as Chip
                chip.text = convertGenreId(movie!!.genre_ids[i])
                genre.addView(chip)
            }

            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500${movie!!.poster_path}")
                .fitCenter()
                .into(i_poster)

            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500${movie!!.backdrop_path}")
                .fitCenter()
                .into(i_backdrop)

            lastStatus = movie!!.favourite
            add_favourite.isChecked = lastStatus
            add_favourite.setBackgroundResource( when(add_favourite.isChecked) {
                true -> R.drawable.favourite_on
                else -> R.drawable.favourite
            })
        }

        add_favourite.setOnClickListener {
            if (add_favourite.isChecked) {
                AlertDialog.Builder(this)
                    .setMessage("Add to Favourite ?")
                    .setPositiveButton("ADD") { _, _ ->
                        add_favourite.isChecked = true
                        add_favourite.setBackgroundResource(R.drawable.favourite_on)
                    }
                    .setNegativeButton("CANCEL") { _, _ ->
                        add_favourite.isChecked = true
                    }
                    .show()
            }
            else {
                AlertDialog.Builder(this)
                    .setMessage("Remove from Favourite ?")
                    .setPositiveButton("REMOVE") { _, _ ->
                        add_favourite.isChecked = false
                        add_favourite.setBackgroundResource(R.drawable.favourite)
                    }
                    .setNegativeButton("CANCEL") { _, _ ->
                        add_favourite.isChecked = true
                    }
                    .show()
            }
        }
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

    override fun onBackPressed() {
//        super.onBackPressed()
        val intent = Intent()
        val data = Bundle()
        if (lastStatus != add_favourite.isChecked) {
            movie?.id?.let {
                data.putInt("ID", it)
                data.putString("FRAG", frag)
                intent.putExtras(data)
            }
            setResult(Activity.RESULT_OK, intent)
        } else {
            setResult(Activity.RESULT_OK)
        }
        finish()
    }

}
