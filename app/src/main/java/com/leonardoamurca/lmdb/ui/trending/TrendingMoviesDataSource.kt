package com.leonardoamurca.lmdb.ui.trending

import com.leonardoamurca.lmdb.model.Movie
import com.leonardoamurca.lmdb.model.NetworkListResponse
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
import com.leonardoamurca.lmdb.ui.MoviesDataSource
import kotlinx.coroutines.CoroutineScope
import retrofit2.Response

class TrendingMoviesDataSource(
    scope: CoroutineScope,
    private val movieApi: MovieApi
) : MoviesDataSource(scope) {
    override suspend fun fetchMovies(page: Int): NetworkState<List<Movie>> {
        return try {
            updateLoading(true)
            val response = movieApi.getTrendingMoviesOf("day", page)
            handleResponse(response)
        } catch (error: Exception) {
            NetworkException(error.message!!)
        } finally {
            updateLoading(false)
        }
    }

    private fun handleResponse(response: Response<NetworkListResponse>): NetworkState<List<Movie>> {
        return if (response.isSuccessful) {
            Success(response.body()?.results!!)
        } else {
            when (response.code()) {
                FORBIDDEN.code -> ResourceForbidden(response.message())
                NOT_FOUND.code -> ResourceNotFound(response.message())
                INTERNAL_SERVER_ERROR.code -> InternalServerError(
                    response.message()
                )
                BAD_GATEWAY.code -> BadGateWay(response.message())
                MOVED_PERMANENTLY.code -> ResourceRemoved(
                    response.message()
                )
                FOUND.code -> RemovedResourceFound(response.message())
                else -> Error(response.message())
            }
        }
    }
}