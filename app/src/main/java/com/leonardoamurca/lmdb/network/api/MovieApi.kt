package com.leonardoamurca.lmdb.network.api

import com.leonardoamurca.lmdb.model.Movie
import com.leonardoamurca.lmdb.model.NetworkListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") movieId: Int): Response<Movie>

    @GET("trending/movie/{period}")
    suspend fun getTrendingMoviesOf(
        @Path("period") period: String,
        @Query("page") pageNumber: Int
    ): Response<NetworkListResponse>

    @GET("discover/movie")
    suspend fun getPopularMovies(
        @Query("language") language: String,
        @Query("sort_by") sortBy: String,
        @Query("include_adult") includeAdult: Boolean,
        @Query("include_video") includeVideo: Boolean,
        @Query("page") pageNumber: Int
    ): Response<NetworkListResponse>
}