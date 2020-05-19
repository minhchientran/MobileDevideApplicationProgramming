package com.example.week3_movielist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

abstract class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    abstract  val backdrop: ImageView
    abstract val title: TextView
    abstract val vote: TextView
}