package com.leonardoamurca.lmdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.leonardoamurca.lmdb.network.Movie

class MovieViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    private val _showLoading = MutableLiveData<Boolean>(false)
    val showLoading: LiveData<Boolean> get() = _showLoading

    fun init(movie: Movie) {
        _movie.value = movie
    }
}