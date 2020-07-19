package com.leonardoamurca.lmdb

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.leonardoamurca.lmdb.network.Movie
import com.leonardoamurca.lmdb.network.MovieApi
import kotlinx.coroutines.CoroutineScope

class MoviesDataSourceFactory(
    private val scope: CoroutineScope,
    private val movieApi: MovieApi
) : DataSource.Factory<Int, Movie>() {

    val dataSource = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val moviesDataSource = MoviesDataSource(scope, movieApi)
        dataSource.postValue(moviesDataSource)

        return moviesDataSource
    }
}