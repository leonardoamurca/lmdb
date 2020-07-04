package com.leonardoamurca.lmdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.leonardoamurca.lmdb.db.MovieRepository
import com.leonardoamurca.lmdb.network.MovieApi

class MovieViewModel(
    app: Application,
    private val movieApi: MovieApi
) : AndroidViewModel(app) {

    private val _title = MutableLiveData<String>("")
    val title: LiveData<String> = _title

    private val _overview = MutableLiveData<String>("")
    val overview: LiveData<String> = _overview

    private val _voteAverage = MutableLiveData<Float>(0F)
    val voteAverage: LiveData<Float> = _voteAverage

    private val _posterImage = MutableLiveData<String>("")
    val posterImage: LiveData<String> = _posterImage

    private val _showLoading = MutableLiveData<Boolean>(false)
    val showLoading: LiveData<Boolean> get() = _showLoading

    suspend fun init(movieId: Int) {
        _showLoading.postValue(true)
        val movie = movieApi.getMovieDetails(movieId)
        _showLoading.postValue(false)
        movie.let {
            _title.value = it.title
            _overview.value = it.overview
            _voteAverage.value = it.voteAverage
            _posterImage.value = it.posterPath
        }
    }
}