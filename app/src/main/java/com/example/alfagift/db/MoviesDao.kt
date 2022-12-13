package com.example.alfagift.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.alfagift.model.db.Movies

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): List<Movies>

    @Insert
    fun insertMovies(vararg movies: Movies)

    @Query("DELETE FROM movies")
    fun deleteAllUser()
}