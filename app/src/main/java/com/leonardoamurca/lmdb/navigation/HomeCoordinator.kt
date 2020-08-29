package com.leonardoamurca.lmdb.navigation

import com.leonardoamurca.lmdb.model.Movie

class HomeCoordinator(private val navigator: Navigator) {

    fun start() {
        navigator.showHomeScreen()
    }

    fun showSelectedMovieDetails(movie: Movie) {
        navigator.showMovieDetails(movie, "Home")
    }

    fun showFavorites() {
        navigator.showTrendingMoviesScreen()
    }
}