package com.example.week3_movielist

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.khtn.androidcamp.DataCenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var layoutManager: GridLayoutManager? = null
    private var adapter: MovieAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutManager = GridLayoutManager(this, 1)
        movie_recyclerview.layoutManager = layoutManager
        var info = convertNestedJsonStringToObject()
        adapter = MovieAdapter(layoutManager, this@MainActivity, info)
        movie_recyclerview.adapter = adapter

    }

    private fun convertNestedJsonStringToObject() : ListMovie{
        return Gson().fromJson(DataCenter.getMovieJsonString(), ListMovie::class.java)
    }

    @SuppressLint("ResourceType")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.layout.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.change_layout -> {
                if (layoutManager?.spanCount == 1) {
                    layoutManager?.spanCount = 2
                    item.title = "list"
                } else {
                    layoutManager?.spanCount = 1
                    item.title = "grid"
                }
                adapter?.notifyItemRangeChanged(0, adapter?.itemCount ?: 0)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
