package com.leonardoamurca.lmdb.ui.trending

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.leonardoamurca.lmdb.model.Movie
import com.leonardoamurca.lmdb.network.api.MovieApi
import com.leonardoamurca.lmdb.network.NetworkState
import com.leonardoamurca.lmdb.network.NetworkState.Success
import com.leonardoamurca.lmdb.network.NetworkState.Error
import com.leonardoamurca.lmdb.network.NetworkState.NetworkException
import com.leonardoamurca.lmdb.network.NetworkState.HttpErrors.ResourceForbidden
import com.leonardoamurca.lmdb.network.NetworkState.HttpErrors.ResourceNotFound
import com.leonardoamurca.lmdb.network.NetworkState.HttpErrors.InternalServerError
import com.leonardoamurca.lmdb.network.NetworkState.HttpErrors.BadGateWay
import com.leonardoamurca.lmdb.network.NetworkState.HttpErrors.ResourceRemoved
import com.leonardoamurca.lmdb.network.NetworkState.HttpErrors.RemovedResourceFound
import com.leonardoamurca.lmdb.network.HttpStatus.MOVED_PERMANENTLY
import com.leonardoamurca.lmdb.network.HttpStatus.FOUND
import com.leonardoamurca.lmdb.network.HttpStatus.BAD_GATEWAY
import com.leonardoamurca.lmdb.network.HttpStatus.INTERNAL_SERVER_ERROR
import com.leonardoamurca.lmdb.network.HttpStatus.NOT_FOUND
import com.leonardoamurca.lmdb.network.HttpStatus.FORBIDDEN
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrendingMoviesDataSource(
    private val scope: CoroutineScope,
    private val movieApi: MovieApi
) : PageKeyedDataSource<Int, Movie>() {

    val loadingState = MutableLiveData<Boolean>(false)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        scope.launch(Dispatchers.IO) {
            val movies = fetchMovies(INITIAL_PAGE)

            if (movies is NetworkState.Success) {
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

    private suspend fun fetchMovies(page: Int): NetworkState<List<Movie>> {
        return try {
            updateLoading(true)
            val response = movieApi.getTrendingMoviesOf("day", page)
            when {
                response.isSuccessful -> Success(response.body()?.results!!)
                else -> when (response.code()) {
                    FORBIDDEN.code -> ResourceForbidden(response.message())
                    NOT_FOUND.code -> ResourceNotFound(response.message())
                    INTERNAL_SERVER_ERROR.code -> InternalServerError(response.message())
                    BAD_GATEWAY.code -> BadGateWay(response.message())
                    MOVED_PERMANENTLY.code -> ResourceRemoved(response.message())
                    FOUND.code -> RemovedResourceFound(response.message())
                    else -> Error(response.message())
                }
            }
        } catch (error: Exception) {
            NetworkException(error.message!!)
        } finally {
            updateLoading(false)
        }
    }

    private fun updateLoading(state: Boolean) {
        loadingState.postValue(state)
    }

    companion object {
        private const val INITIAL_PAGE = 1
    }
}