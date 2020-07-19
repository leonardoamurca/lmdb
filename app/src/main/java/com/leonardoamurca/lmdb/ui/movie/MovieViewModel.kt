package com.leonardoamurca.lmdb.ui.movie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.leonardoamurca.lmdb.model.Movie

class MovieViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    fun init(movie: Movie) {
        _movie.value = movie
    }
}