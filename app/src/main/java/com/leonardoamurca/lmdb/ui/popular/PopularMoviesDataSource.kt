package com.leonardoamurca.lmdb.ui.popular

import com.leonardoamurca.lmdb.model.Movie
import com.leonardoamurca.lmdb.network.NetworkState
import com.leonardoamurca.lmdb.network.NetworkState.NetworkException
import com.leonardoamurca.lmdb.network.api.MovieApi
import com.leonardoamurca.lmdb.ui.MoviesDataSource
import com.leonardoamurca.lmdb.utils.handleListStateResponse
import kotlinx.coroutines.CoroutineScope

class PopularMoviesDataSource(
    scope: CoroutineScope,
    private val movieApi: MovieApi
) : MoviesDataSource(scope) {
    override suspend fun fetchMovies(page: Int): NetworkState<List<Movie>> {
        return try {
            updateLoading(true)
            val response = movieApi.getPopularMovies(
                language = "en-US",
                sortBy = "popularity.ascdesc",
                includeAdult = false,
                includeVideo = false,
                pageNumber = page
            )
            response.handleListStateResponse()
        } catch (error: Exception) {
            NetworkException(error.message!!)
        } finally {
            updateLoading(false)
        }
    }
}