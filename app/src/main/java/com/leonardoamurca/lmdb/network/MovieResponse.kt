package com.leonardoamurca.lmdb.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResponse(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "title")
    val title: String = "",
    @Json(name = "overview")
    val overview: String = "",
    @Json(name = "poster_path")
    val posterPath: String = "",
    @Json(name = "vote_average")
    val voteAverage: Float = 0F
)