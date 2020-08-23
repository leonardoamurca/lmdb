package com.leonardoamurca.lmdb.model

data class Collection(
    val title: String = "",
    val children: List<Movie>?
)