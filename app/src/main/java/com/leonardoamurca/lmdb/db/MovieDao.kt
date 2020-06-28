package com.leonardoamurca.lmdb.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert
    fun insert(movie: MovieEntity)

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieBy(id: Int): MovieEntity?

    @Query("SELECT * FROM movies")
    fun getMovies(): List<MovieEntity>
}