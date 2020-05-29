package com.example.week4_moviefavourite

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.week4_moviefavourite.room.AppDatabase
import com.example.week4_moviefavourite.room.MovieDAO
import com.example.week4_moviefavourite.ui.movie.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

var requestCode = 123

class MainActivity : AppCompatActivity() {

    private var state : Int = 0
    private lateinit var dao: MovieDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        initRoomDatabase()
        getMovieFromDatabase()
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
//        val appBarConfiguration = AppBarConfiguration(setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val sharedPreferences = getSharedPreferences("STATE", Context.MODE_PRIVATE)
        state = sharedPreferences.getInt("STATE", 0)
        navController.navigate( when (state) {
            0 -> R.id.navigation_home
            1 -> R.id.navigation_dashboard
            2 -> R.id.navigation_notifications
            else ->  R.id.navigation_home
        })
        navView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    state = 0
                    navController.navigate(R.id.navigation_home)
                }
                R.id.navigation_dashboard -> {
                    state = 1
                    navController.navigate(R.id.navigation_dashboard)
                }
                R.id.navigation_notifications -> {
                    state = 2
                    navController.navigate(R.id.navigation_notifications)
                }
                else -> {}
            }
            sharedPreferences.edit().putInt("STATE", state).apply()
            true
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.change_layout -> {
                    if (layoutManager?.spanCount == 1) {
                        spanCount = 3
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
                    when (it.getString("FRAG")) {
                        "Now" -> {
                            val movie =
                                listNowPlayingMovie.movie.single { it.id == id }
                            val index = listNowPlayingMovie.movie.indexOf(movie)
                            val status = listNowPlayingMovie.movie[index].favourite
                            listNowPlayingMovie.movie[index].favourite = !status
                            adapter?.notifyItemChanged(index)
                            when (status) {
                                true -> {
                                    val movieFR =
                                        listFavouriteMovie.movie.single { it.id == id }
                                    val indexFR = listFavouriteMovie.movie.indexOf(movieFR)
                                    listFavouriteMovie.movie.removeAt(indexFR)
                                    dao.delete(movie)
                                }
                                else -> {
                                    listFavouriteMovie.movie.add(movie)
                                    dao.insert(movie)
                                }
                            }
                        }
                        "Rating" -> {
                            val movie =
                                listTopRatingMovie.movie.single { it.id == id }
                            val index = listTopRatingMovie.movie.indexOf(movie)
                            val status = listTopRatingMovie.movie[index].favourite
                            listTopRatingMovie.movie[index].favourite = !status
                            when (status) {
                                true -> {
                                    val movieFR =
                                        listFavouriteMovie.movie.single { it.id == id }
                                    val indexFR = listFavouriteMovie.movie.indexOf(movieFR)
                                    listFavouriteMovie.movie.removeAt(indexFR)
                                    dao.delete(movie)
                                }
                                else -> {
                                    listFavouriteMovie.movie.add(movie)
                                    dao.insert(movie)
                                }
                            }
                            adapter?.notifyItemChanged(index)
                        }
                        else -> {
                            val movie =
                                listFavouriteMovie.movie.single{ it.id == id }
                            var index = listFavouriteMovie.movie.indexOf(movie)
                            dao.delete(listFavouriteMovie.movie[index])
                            listFavouriteMovie.movie.remove(movie)
                            adapter?.notifyItemRemoved(index)

                            val movieNP  =
                                listNowPlayingMovie.movie.singleOrNull{ it.id == id }
                            movieNP?.let {
                                index = listNowPlayingMovie.movie.indexOf(movieNP)
                                listNowPlayingMovie.movie[index].favourite =
                                    !(listNowPlayingMovie.movie[index].favourite)
                            }

                            val movieTR =
                                listTopRatingMovie.movie.singleOrNull{ it.id == id }
                            movieTR?.let {
                                index = listTopRatingMovie.movie.indexOf(movieTR)
                                listTopRatingMovie.movie[index].favourite =
                                    !(listTopRatingMovie.movie[index].favourite)
                            }
                        }
                    }

                }
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }

    private fun initRoomDatabase() {
        val db = AppDatabase.invoke(this)
        dao = db.movieDAO()
    }

    private fun getMovieFromDatabase() {
        val students = dao.getAll() // get Students from ROOM database
        listFavouriteMovie.movie.addAll(students) // add to student list
        for (movie in listFavouriteMovie.movie) {
            val movieNP = listNowPlayingMovie.movie.singleOrNull{ it.id == movie.id }
            movieNP?.let {
                val index = listNowPlayingMovie.movie.indexOf(movieNP)
                listNowPlayingMovie.movie[index].favourite = true
            }

            val movieTR = listTopRatingMovie.movie.singleOrNull{ it.id == movie.id }
            movieTR?.let {
                val index = listTopRatingMovie.movie.indexOf(movieTR)
                listTopRatingMovie.movie[index].favourite = true
            }
        }
        adapter?.notifyDataSetChanged() // notify data changed
    }
}
