package com.leonardoamurca.lmdb.navigation

import com.leonardoamurca.lmdb.model.Movie

class PopularMoviesCoordinator(private val navigator: Navigator) {

    fun start() {
        navigator.showPopularMoviesScreen()
    }

    fun showSelectedMovieDetails(movie: Movie) {
        navigator.showMovieDetails(movie)
    }

    fun closePopularMovies() {
        navigator.closePopularMovies()
    }
}