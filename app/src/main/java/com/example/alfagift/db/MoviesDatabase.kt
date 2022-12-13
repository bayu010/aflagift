package com.example.alfagift.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.alfagift.model.db.Movies

@Database(entities = [Movies::class], version = 1)
abstract class MoviesDatabase: RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

}