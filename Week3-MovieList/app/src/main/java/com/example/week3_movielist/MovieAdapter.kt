package com.example.week3_movielist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieAdapter (
    private val layoutManager: GridLayoutManager? = null,
    private val ctx: Context,
    private var data: ListMovie
    ) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return if (viewType == 0)
            ListMovieViewHolder(LayoutInflater.from(ctx).inflate(R.layout.list_viewholder, parent, false))
        else GridMovieViewHolder(LayoutInflater.from(ctx).inflate(R.layout.grid_viewholder, parent, false))
    }

    override fun getItemCount(): Int {
        return data.movie.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = data.movie[position]
        Glide.with(ctx)
            .load("https://image.tmdb.org/t/p/w500"+movie.backdrop_path)
            .override(350,200)
            .into(holder.backdrop)
        holder.title.text = movie.title
    }

    enum class ViewType {
        LIST,
        GRID
    }

    override fun getItemViewType(position: Int): Int {
        return if (layoutManager?.spanCount == 1) ViewType.LIST.ordinal
        else ViewType.GRID.ordinal
    }
}