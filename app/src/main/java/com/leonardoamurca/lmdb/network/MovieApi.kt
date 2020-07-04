package com.leonardoamurca.lmdb.network

import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") movieId: Int): MovieResponse
}