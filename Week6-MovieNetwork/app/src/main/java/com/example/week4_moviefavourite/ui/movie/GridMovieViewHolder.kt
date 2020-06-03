package com.example.week3_movielist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.week4_moviefavourite.R
import com.example.week4_moviefavourite.ui.movie.MovieViewHolder

class GridMovieViewHolder(itemView: View) : MovieViewHolder(itemView) {
    override val backdrop: ImageView = itemView.findViewById(R.id.grid_backdrop)
    override val title: TextView = itemView.findViewById(R.id.grid_title)
    override val vote: TextView = itemView.findViewById(R.id.grid_vote)
}