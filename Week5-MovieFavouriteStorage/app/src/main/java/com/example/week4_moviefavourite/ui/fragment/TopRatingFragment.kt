package com.example.week4_moviefavourite.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.week4_moviefavourite.MovieInfoActivity
import com.example.week4_moviefavourite.R
import com.example.week4_moviefavourite.requestCode
import com.example.week4_moviefavourite.ui.movie.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class TopRatingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        layoutManager = GridLayoutManager(activity, spanCount)
        root.movie_recyclerview.layoutManager = layoutManager

        adapter = activity?.let { MovieAdapter(layoutManager, it, listTopRatingMovie) }

        root.movie_recyclerview.adapter = adapter
        adapter?.listener = object: MovieAdapter.MovieListener {
            override fun onClickListener(movie: ListMovie.Movie) {
                val intent = Intent(activity, MovieInfoActivity::class.java)
                val movieInfo = convertNestedObjectToJsonString(movie)
                intent.putExtra("MOVIE_INFO", movieInfo)
                intent.putExtra("Fragment", "Rating")
                activity?.startActivityForResult(intent,  requestCode)
            }
        }

        return root
    }

}
