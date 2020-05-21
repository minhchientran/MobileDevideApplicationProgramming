package com.example.week4_moviefavourite

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.ui.setupWithNavController
import com.example.week4_moviefavourite.ui.dashboard.DashboardFragment
import com.example.week4_moviefavourite.ui.dashboard.topRatingList
import com.example.week4_moviefavourite.ui.home.nowPlayingList
import com.example.week4_moviefavourite.ui.movie.*
import com.example.week4_moviefavourite.ui.notifications.favouriteList
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

var requestCode = 123

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
//        val appBarConfiguration = AppBarConfiguration(setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.change_layout -> {
                    if (layoutManager?.spanCount == 1) {
                        spanCount = 3;
                        layoutManager?.spanCount = spanCount
                        menuItem.setIcon(R.drawable.ic_view_list_black_24dp)
                    } else {
                        spanCount = 1
                        layoutManager?.spanCount = spanCount
                        menuItem.setIcon(R.drawable.ic_apps_black_24dp)
                    }
                    adapter?.notifyItemRangeChanged(0, adapter?.itemCount ?: 0)
                    true
                }
                else -> false
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123 ) {
            if (resultCode == Activity.RESULT_OK) {
                val bundle = data?.extras
                bundle?.let {
                    val id = it.getInt("ID")
                    val frag = it.getString("FRAG")
                    val movie = listMovie.movie.single { it.id == id }
                    val index = listMovie.movie.indexOf(movie)
                    listMovie.movie[index].favourite = !listMovie.movie[index].favourite
                    when (frag) {
                        "Now" -> notifyItemChangeFragment(nowPlayingList, id)
                        "Rating" -> notifyItemChangeFragment(topRatingList, id)
                        "Favourite" -> {
                            val movie = favouriteList?.movie?.single{ it.id == id }!!
                            val index = favouriteList?.movie?.indexOf(movie)!!
                            favouriteList?.movie?.remove(movie)
                            adapter?.notifyItemRemoved(index)
                        }
                        else -> {}
                    }

                }
            }
        }
    }

    private fun notifyItemChangeFragment(listMovie: ListMovie?, id : Int) {
        val movie = listMovie?.movie?.single { it.id == id }!!
        val index = listMovie.movie.indexOf(movie)
        listMovie.movie[index].favourite = !(listMovie.movie[index].favourite)
        adapter?.notifyItemChanged(index)
    }
}
