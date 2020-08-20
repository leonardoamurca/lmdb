package com.leonardoamurca.lmdb.navigation

import com.leonardoamurca.lmdb.model.Movie

class TrendingMoviesCoordinator(
    private val navigator: Navigator,
    private val movieDetailsCoordinador: MovieDetailsCoordinador
) {

    fun start() {
        navigator.showTrendingMoviesScreen()
    }

    fun showSelectedMovieDetails(movie: Movie) {
        movieDetailsCoordinador.start(movie, "TrendingMovies")
    }

    fun closeTrendingMovies() {
        navigator.goBack()
    }
}