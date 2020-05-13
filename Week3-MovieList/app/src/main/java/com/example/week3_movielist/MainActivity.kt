package com.example.week3_movielist

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.khtn.androidcamp.DataCenter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var layoutManager: GridLayoutManager? = null
    private var adapter: MovieAdapter? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        layoutManager = GridLayoutManager(this, 1)
        movie_recyclerview.layoutManager = layoutManager
        var info = convertNestedJsonStringToObject()
        adapter = MovieAdapter(layoutManager, this@MainActivity, info)
        movie_recyclerview.adapter = adapter
        adapter?.listener = object: MovieAdapter.MovieListener {
            override fun onClickListener(movie: ListMovie.Movie) {
                val intent = Intent(this@MainActivity, MovieInfoActivity::class.java)
                val movieInfo = convertNestedObjectToJsonString(movie)
                intent.putExtra("MOVIE_INFO", movieInfo)
                startActivity(intent)
            }
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.change_layout -> {
                    if (layoutManager?.spanCount == 1) {
                        layoutManager?.spanCount = 3
                        menuItem.setIcon(R.drawable.ic_view_list_black_24dp)
                    } else {
                        layoutManager?.spanCount = 1
                        menuItem.setIcon(R.drawable.ic_apps_black_24dp)
                    }
                    adapter?.notifyItemRangeChanged(0, adapter?.itemCount ?: 0)
                    true
                }
                else -> false
            }
        }

    }

    private fun convertNestedJsonStringToObject() : ListMovie{
        return Gson().fromJson(DataCenter.getMovieJsonString(), ListMovie::class.java)
    }

    private fun convertNestedObjectToJsonString(movie : ListMovie.Movie): String? {
        return Gson().toJson(movie)
    }

}
