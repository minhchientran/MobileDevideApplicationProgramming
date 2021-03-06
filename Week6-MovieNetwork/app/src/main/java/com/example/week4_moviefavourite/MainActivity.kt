package com.example.week4_moviefavourite

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.week4_moviefavourite.room.AppDatabase
import com.example.week4_moviefavourite.room.MovieDAO
import com.example.week4_moviefavourite.ui.movie.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.movie_recyclerview
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var requestCode = 123

class MainActivity : AppCompatActivity() {
    private var state : Int = 0
    private lateinit var dao: MovieDAO
    private lateinit var navController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        initRoomDatabase()
        getMovieFromDatabase()
        val sharedPreferences = getSharedPreferences("STATE", Context.MODE_PRIVATE)
        state = sharedPreferences.getInt("STATE", 0)
        getNowPlayingFromApi(null, 1)
        getTopRatingFromApi({ setFavouriteAll() }, 1)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

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
                else -> {
                    state = 2
                    navController.navigate(R.id.navigation_notifications)
                }

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
        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            val bundle = data?.extras
            bundle?.let {
                val id = it.getInt("ID")
                when (it.getString("FRAG")) {
                    "Now" -> changeFavouriteMovie(listNowPlayingMovie, id)
                    "Rating" -> changeFavouriteMovie(listTopRatingMovie, id)
                    else -> {
                        val movie = listFavouriteMovie.movie.single{ it.id == id }
                        var index = listFavouriteMovie.movie.indexOf(movie)
                        dao.delete(listFavouriteMovie.movie[index])
                        listFavouriteMovie.movie.remove(movie)
                        adapter?.notifyItemRemoved(index)

                        val movieNP =
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

    private fun changeFavouriteMovie(listMovie: ListMovie, id: Int) {
        val movie = listMovie.movie.single { it.id == id }
        val index = listMovie.movie.indexOf(movie)
        val status = listMovie.movie[index].favourite
        listMovie.movie[index].favourite = !status
        adapter?.notifyItemChanged(index)
        when (status) {
            true -> {
                val movieFR = listFavouriteMovie.movie.single { it.id == id }
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
    }

    private fun setFavouriteAll() {
        for (movie in listFavouriteMovie.movie) {
            setFavouriteOn(listNowPlayingMovie, movie)
            setFavouriteOn(listTopRatingMovie, movie)
        }
        adapter?.notifyDataSetChanged()
        navController.navigate( when (state) {
            0 -> R.id.navigation_home
            1 -> R.id.navigation_dashboard
            2 -> R.id.navigation_notifications
            else ->  R.id.navigation_home
        })
    }

    private fun setFavouriteOn(listMovie: ListMovie, movie: ListMovie.Movie) {
        val movieF = listMovie.movie.singleOrNull{ it.id == movie.id }
        movieF?.let {
            val index = listMovie.movie.indexOf(movieF)
            listMovie.movie[index].favourite = true
        }
    }



}
