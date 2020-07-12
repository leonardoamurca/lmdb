package com.leonardoamurca.lmdb.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") movieId: Int): Response<Movie>

    @GET("trending/movie/day")
    suspend fun getTrendingMoviesOf(
        @Path("period") period: String,
        @Query("page") pageNumber: Int
    ): Response<NetworkListResponse>
}