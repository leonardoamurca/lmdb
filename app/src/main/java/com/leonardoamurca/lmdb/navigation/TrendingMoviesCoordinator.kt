package com.leonardoamurca.lmdb.navigation

import com.leonardoamurca.lmdb.model.Movie

class TrendingMoviesCoordinator(private val navigator: Navigator) {

    fun start() {
        navigator.showTrendingMoviesScreen()
    }

    fun showSelectedMovieDetails(movie: Movie) {
        navigator.showMovieDetails(movie)
    }
}