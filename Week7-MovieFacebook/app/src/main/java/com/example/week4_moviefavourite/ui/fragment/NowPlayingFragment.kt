package com.example.week4_moviefavourite.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week4_moviefavourite.*
import com.example.week4_moviefavourite.ui.movie.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import kotlin.properties.Delegates

class NowPlayingFragment : Fragment() {

    var pageNumber = 1
    var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        layoutManager = GridLayoutManager(activity, spanCount)
        root.movie_recyclerview.layoutManager = layoutManager

        adapter = activity?.let { MovieAdapter(layoutManager, it, listNowPlayingMovie) }
        root.movie_recyclerview.adapter = adapter

        adapter?.listener = object: MovieAdapter.MovieListener {
            override fun onClickListener(movie: ListMovie.Movie) {
                val intent = Intent(activity, MovieInfoActivity::class.java)
                val movieInfo = convertNestedObjectToJsonString(movie)
                intent.putExtra("MOVIE_INFO", movieInfo)
                intent.putExtra("Fragment", "Now")
                activity?.startActivityForResult(intent,  requestCode)
            }
        }

        root.movie_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount = layoutManager?.childCount
                    val pastVisibleItem = layoutManager?.findFirstCompletelyVisibleItemPosition()
                    val totalItemCount = adapter?.itemCount
                    println(listNowPlayingMovie.totalPages)
                    if (!isLoading && pageNumber <= listNowPlayingMovie.totalPages) {
                        if (visibleItemCount!! + pastVisibleItem!! >= totalItemCount!!
                            && pastVisibleItem >= 0) {
                            isLoading = true
                            pageNumber += 1
                            getNowPlayingFromApi({
                                adapter?.notifyDataSetChanged()
                                isLoading = false
                            }, pageNumber)
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        return root
    }
}
