package com.leonardoamurca.lmdb.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(private val movieDao: MovieDao) : MovieRepository {
    override suspend fun getMovie(forMovieWithId: Int): MovieEntity? {
        return withContext(Dispatchers.IO) {
            movieDao.getMovieBy(forMovieWithId)
        }
    }

    override suspend fun getAllMovies(): List<MovieEntity> {
        return withContext(Dispatchers.IO) {
            movieDao.getMovies()
        }
    }
}