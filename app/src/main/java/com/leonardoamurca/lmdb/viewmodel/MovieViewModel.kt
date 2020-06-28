package com.leonardoamurca.lmdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.leonardoamurca.lmdb.db.MovieRepository

class MovieViewModel(
    app: Application,
    private val movieRepository: MovieRepository
) : AndroidViewModel(app) {
    private val _title = MutableLiveData<String>("")
    val title: LiveData<String> = _title

    suspend fun init() {
        val movie = movieRepository.getMovie(1)
        movie?.let {
            _title.value = movie.title
        }
    }
}