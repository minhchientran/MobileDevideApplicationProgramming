package com.example.week3_movielist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.week4_moviefavourite.R

class ListMovieViewHolder(itemView: View) : MovieViewHolder(itemView) {
    override val backdrop: ImageView = itemView.findViewById<ImageView>(R.id.list_backdrop)
    override val title: TextView = itemView.findViewById<TextView>(R.id.list_title)
    override val vote: TextView = itemView.findViewById(R.id.list_vote)
}