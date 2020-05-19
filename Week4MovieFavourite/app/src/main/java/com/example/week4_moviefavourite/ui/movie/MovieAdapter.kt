package com.example.week4_moviefavorite.ui.movie

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.week3_movielist.GridMovieViewHolder
import com.example.week3_movielist.ListMovie
import com.example.week3_movielist.ListMovieViewHolder
import com.example.week3_movielist.MovieViewHolder
import com.example.week4_moviefavourite.R
import com.google.gson.Gson
import com.khtn.androidcamp.DataCenter

var layoutManager: GridLayoutManager? = null
var adapter: MovieAdapter? = null
var spanCount = 1;

fun convertNestedJsonStringToObject() : ListMovie {
    return Gson().fromJson(DataCenter.getMovieJsonString(), ListMovie::class.java)
}

fun convertNestedObjectToJsonString(movie : ListMovie.Movie): String? {
    return Gson().toJson(movie)
}

class MovieAdapter (
    private val layoutManager: GridLayoutManager? = null,
    private val ctx: Context,
    private var data: ListMovie
    ) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return if (viewType == 0)
            ListMovieViewHolder(LayoutInflater.from(ctx)
                .inflate(R.layout.list_viewholder, parent, false))
        else GridMovieViewHolder(LayoutInflater.from(ctx)
                .inflate(R.layout.grid_viewholder, parent, false))
    }

    override fun getItemCount(): Int {
        return data.movie.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = data.movie[position]
        Glide.with(ctx)
            .load("https://image.tmdb.org/t/p/w500${if (getItemViewType(position) == 0)
                                                                movie.backdrop_path
                                                            else
                                                                movie.poster_path}")
            .fitCenter()
            .into(holder.backdrop)
        holder.title.text = movie.title
        holder.vote.text =  "${movie.vote_average.toString()}☆"

        holder.itemView.setOnClickListener {
            listener?.onClickListener(movie)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (layoutManager?.spanCount == 1) 0
        else 1
    }

    var listener: MovieListener? = null

    interface MovieListener {
        fun  onClickListener(movie: ListMovie.Movie)
    }
}