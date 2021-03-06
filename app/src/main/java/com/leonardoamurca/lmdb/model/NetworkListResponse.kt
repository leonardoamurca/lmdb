package com.leonardoamurca.lmdb.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkListResponse(
    @Json(name = "results")
    val results: List<Movie>
)