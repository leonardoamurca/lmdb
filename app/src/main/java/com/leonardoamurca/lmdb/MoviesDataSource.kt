package com.leonardoamurca.lmdb

import androidx.paging.PageKeyedDataSource
import com.leonardoamurca.lmdb.network.Movie
import com.leonardoamurca.lmdb.network.MovieApi
import com.leonardoamurca.lmdb.network.NetworkState
import com.leonardoamurca.lmdb.utils.HttpStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesDataSource(
    private val scope: CoroutineScope,
    private val movieApi: MovieApi
) :
    PageKeyedDataSource<Int, Movie>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        scope.launch(Dispatchers.IO) {
            val movies = fetchMovies(INITIAL_PAGE)

            if (movies is NetworkState.Success) {
                callback.onResult(movies.data, null, INITIAL_PAGE + 1)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        scope.launch(Dispatchers.IO) {
            val movies = fetchMovies(params.key)

            if (movies is NetworkState.Success) {
                callback.onResult(movies.data, params.key + 1)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        // Ignored, since we only ever append to our initial load
    }

    private suspend fun fetchMovies(page: Int): NetworkState<List<Movie>> {
        return try {
            val response = movieApi.getTrendingMoviesOf("day", page)
            when {
                response.isSuccessful -> NetworkState.Success(response.body()?.results!!)
                else -> {
                    when (response.code()) {
                        HttpStatus.FORBIDDEN.code -> NetworkState.HttpErrors.ResourceForbidden(
                            response.message()
                        )
                        HttpStatus.NOT_FOUND.code -> NetworkState.HttpErrors.ResourceNotFound(
                            response.message()
                        )
                        HttpStatus.INTERNAL_SERVER_ERROR.code -> NetworkState.HttpErrors.InternalServerError(
                            response.message()
                        )
                        HttpStatus.BAD_GATEWAY.code -> NetworkState.HttpErrors.BadGateWay(
                            response.message()
                        )
                        HttpStatus.MOVED_PERMANENTLY.code -> NetworkState.HttpErrors.ResourceRemoved(
                            response.message()
                        )
                        HttpStatus.FOUND.code -> NetworkState.HttpErrors.RemovedResourceFound(
                            response.message()
                        )
                        else -> NetworkState.Error(response.message())
                    }
                }
            }
        } catch (error: Exception) {
            NetworkState.NetworkException(error.message!!)
        }
    }

    companion object {
        private const val INITIAL_PAGE = 1
    }
}