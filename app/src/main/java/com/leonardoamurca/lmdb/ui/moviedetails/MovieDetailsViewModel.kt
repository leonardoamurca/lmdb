package com.leonardoamurca.lmdb.ui.moviedetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.leonardoamurca.lmdb.model.Movie
import com.leonardoamurca.lmdb.navigation.MovieDetailsCoordinador

class MovieDetailsViewModel(
    app: Application,
    private val coordinador: MovieDetailsCoordinador
) : AndroidViewModel(app) {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    fun init(movie: Movie?) {
        _movie.value = movie
    }

    fun closeMovieDetails() {
        coordinador.goBack()
    }
}