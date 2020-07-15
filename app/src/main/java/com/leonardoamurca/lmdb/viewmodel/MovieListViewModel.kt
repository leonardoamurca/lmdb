package com.leonardoamurca.lmdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.leonardoamurca.lmdb.network.Movie
import com.leonardoamurca.lmdb.network.MovieApi
import com.leonardoamurca.lmdb.network.NetworkState
import com.leonardoamurca.lmdb.utils.HttpStatus.FORBIDDEN
import com.leonardoamurca.lmdb.utils.HttpStatus.NOT_FOUND
import com.leonardoamurca.lmdb.utils.HttpStatus.INTERNAL_SERVER_ERROR
import com.leonardoamurca.lmdb.utils.HttpStatus.BAD_GATEWAY
import com.leonardoamurca.lmdb.utils.HttpStatus.FOUND
import com.leonardoamurca.lmdb.utils.HttpStatus.MOVED_PERMANENTLY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListViewModel(
    app: Application,
    private val movieApi: MovieApi
) : AndroidViewModel(app) {

    private val _showLoading = MutableLiveData<Boolean>(false)
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    init {
        viewModelScope.launch(Dispatchers.Main) {
            val movies = fetchMovies()

            if (movies is NetworkState.Success) {
                _movies.value = movies.data
            }
        }
    }

    private suspend fun fetchMovies(): NetworkState<List<Movie>> {
        return try {
            _showLoading.postValue(true)
            val response = movieApi.getTrendingMoviesOf("day", 1)
            when {
                response.isSuccessful -> NetworkState.Success(response.body()?.results!!)
                else -> {
                    when (response.code()) {
                        FORBIDDEN.code -> NetworkState.HttpErrors.ResourceForbidden(response.message())
                        NOT_FOUND.code -> NetworkState.HttpErrors.ResourceNotFound(response.message())
                        INTERNAL_SERVER_ERROR.code -> NetworkState.HttpErrors.InternalServerError(
                            response.message()
                        )
                        BAD_GATEWAY.code -> NetworkState.HttpErrors.BadGateWay(response.message())
                        MOVED_PERMANENTLY.code -> NetworkState.HttpErrors.ResourceRemoved(response.message())
                        FOUND.code -> NetworkState.HttpErrors.RemovedResourceFound(response.message())
                        else -> NetworkState.Error(response.message())
                    }
                }
            }

        } catch (error: Exception) {
            NetworkState.NetworkException(error.message!!)
        } finally {
            _showLoading.postValue(false)
        }
    }
}