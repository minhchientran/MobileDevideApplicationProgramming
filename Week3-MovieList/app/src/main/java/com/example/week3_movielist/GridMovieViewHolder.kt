package com.example.week3_movielist

import android.view.View
import android.widget.ImageView
import android.widget.TextView

class GridMovieViewHolder(itemView: View) : MovieViewHolder(itemView) {
    override val backdrop: ImageView = itemView.findViewById(R.id.grid_backdrop)
    override val title: TextView = itemView.findViewById(R.id.grid_title)
}