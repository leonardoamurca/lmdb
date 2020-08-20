package com.leonardoamurca.lmdb.navigation

import com.leonardoamurca.lmdb.model.Movie

class MovieDetailsCoordinador(private val navigator: Navigator) {

    fun start(movie: Movie, from: String) {
        navigator.showMovieDetails(movie, from)
    }

    fun goBack() {
        navigator.goBack()
    }
}