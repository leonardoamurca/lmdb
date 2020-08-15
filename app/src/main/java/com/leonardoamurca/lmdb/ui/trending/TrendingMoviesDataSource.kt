package com.leonardoamurca.lmdb.ui.trending

import com.leonardoamurca.lmdb.model.Movie
import com.leonardoamurca.lmdb.network.api.MovieApi
import com.leonardoamurca.lmdb.network.NetworkState
import com.leonardoamurca.lmdb.network.NetworkState.NetworkException
import com.leonardoamurca.lmdb.ui.MoviesDataSource
import com.leonardoamurca.lmdb.utils.handleListStateResponse
import kotlinx.coroutines.CoroutineScope

class TrendingMoviesDataSource(
    scope: CoroutineScope,
    private val movieApi: MovieApi
) : MoviesDataSource(scope) {
    override suspend fun fetchMovies(page: Int): NetworkState<List<Movie>> {
        return try {
            updateLoading(true)
            val response = movieApi.getTrendingMoviesOf("day", page)
            response.handleListStateResponse()
        } catch (error: Exception) {
            NetworkException(error.message!!)
        } finally {
            updateLoading(false)
        }
    }
}