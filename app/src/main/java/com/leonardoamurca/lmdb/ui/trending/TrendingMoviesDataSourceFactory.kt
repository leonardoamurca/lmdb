package com.leonardoamurca.lmdb.ui.trending

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.leonardoamurca.lmdb.model.Movie
import com.leonardoamurca.lmdb.network.api.MovieApi
import kotlinx.coroutines.CoroutineScope

class TrendingMoviesDataSourceFactory(
    private val scope: CoroutineScope,
    private val movieApi: MovieApi
) : DataSource.Factory<Int, Movie>() {

    val dataSource = MutableLiveData<TrendingMoviesDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val moviesDataSource =
            TrendingMoviesDataSource(scope, movieApi)
        dataSource.postValue(moviesDataSource)

        return moviesDataSource
    }
}