package com.leonardoamurca.lmdb.db.repository

import com.leonardoamurca.lmdb.db.entity.MovieEntity

interface MovieRepository {
    suspend fun getMovie(forMovieWithId: Int): MovieEntity?
    suspend fun getAllMovies(): List<MovieEntity>
}