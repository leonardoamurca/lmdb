package com.leonardoamurca.lmdb.ui.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.leonardoamurca.lmdb.model.Movie
import com.leonardoamurca.lmdb.ui.datasource.MoviesDataSource

class MoviesDataSourceFactory(
    private val moviesDataSource: MoviesDataSource
) : DataSource.Factory<Int, Movie>() {

    val dataSource = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, Movie> {
        dataSource.postValue(moviesDataSource)

        return moviesDataSource
    }
}