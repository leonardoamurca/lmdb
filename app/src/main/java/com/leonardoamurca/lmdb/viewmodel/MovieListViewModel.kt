package com.leonardoamurca.lmdb.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.leonardoamurca.lmdb.MoviesDataSource
import com.leonardoamurca.lmdb.MoviesDataSourceFactory
import com.leonardoamurca.lmdb.network.Movie
import com.leonardoamurca.lmdb.network.MovieApi

class MovieListViewModel(
    app: Application,
    movieApi: MovieApi
) : AndroidViewModel(app) {

    var movies: LiveData<PagedList<Movie>>

    private val moviesDataSourceFactory: MoviesDataSourceFactory =
        MoviesDataSourceFactory(viewModelScope, movieApi)

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .build()

        movies = LivePagedListBuilder<Int, Movie>(moviesDataSourceFactory, config).build()
    }

    fun showLoading(): LiveData<Boolean> = Transformations.switchMap(
        moviesDataSourceFactory.dataSource,
        MoviesDataSource::loadingState
    )
}