package com.leonardoamurca.lmdb.db

interface MovieRepository {
    suspend fun getMovie(forMovieWithId: Int): MovieEntity?
    suspend fun getAllMovies(): List<MovieEntity>
}