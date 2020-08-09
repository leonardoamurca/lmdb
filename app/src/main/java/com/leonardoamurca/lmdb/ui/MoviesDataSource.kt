package com.leonardoamurca.lmdb.ui

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.leonardoamurca.lmdb.model.Movie
import com.leonardoamurca.lmdb.network.NetworkState
import com.leonardoamurca.lmdb.network.NetworkState.Success
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class MoviesDataSource(
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Movie>() {
    val loadingState = MutableLiveData<Boolean>(false)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        scope.launch(Dispatchers.IO) {
            val movies = fetchMovies(INITIAL_PAGE)

            if (movies is Success) {
                callback.onResult(
                    movies.data,
                    null,
                    INITIAL_PAGE + 1
                )
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        scope.launch(Dispatchers.IO) {
            val movies = fetchMovies(params.key)

            if (movies is Success) {
                callback.onResult(
                    movies.data,
                    params.key + 1
                )
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        // Ignored, since we only ever append to our initial load
    }

    abstract suspend fun fetchMovies(page: Int): NetworkState<List<Movie>>

    protected fun updateLoading(state: Boolean) {
        loadingState.postValue(state)
    }

    companion object {
        private const val INITIAL_PAGE = 1
    }
}