package com.example.week4_moviefavourite.room

import androidx.room.*
import com.example.week4_moviefavourite.ui.movie.ListMovie

@Dao
interface MovieDAO {
    @Query("SELECT * FROM movie_table")
    fun getAll(): List<ListMovie.Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: ListMovie.Movie): Long

    @Delete
    fun delete(movie: ListMovie.Movie)
}