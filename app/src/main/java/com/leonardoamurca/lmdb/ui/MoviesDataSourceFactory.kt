package com.leonardoamurca.lmdb.ui

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.leonardoamurca.lmdb.model.Movie
import com.leonardoamurca.lmdb.network.api.MovieApi
import com.leonardoamurca.lmdb.ui.trending.TrendingMoviesDataSource
import kotlinx.coroutines.CoroutineScope

class MoviesDataSourceFactory(
    private val moviesDataSource: MoviesDataSource
) : DataSource.Factory<Int, Movie>() {

    val dataSource = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, Movie> {
        dataSource.postValue(moviesDataSource)

        return moviesDataSource
    }
}