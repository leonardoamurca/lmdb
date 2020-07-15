package com.leonardoamurca.lmdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.leonardoamurca.lmdb.MoviesDataSource
import com.leonardoamurca.lmdb.network.Movie
import com.leonardoamurca.lmdb.network.MovieApi

class MovieListViewModel(
    app: Application,
    private val movieApi: MovieApi
) : AndroidViewModel(app) {

    private val _showLoading = MutableLiveData<Boolean>(false)
    val showLoading: LiveData<Boolean> get() = _showLoading

    var movies: LiveData<PagedList<Movie>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .build()

        movies = initializePagedListBuilder(config).build()

    }

    private fun initializePagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, Movie> {
        val dataSourceFactory = object : DataSource.Factory<Int, Movie>() {
            override fun create(): DataSource<Int, Movie> {
                return MoviesDataSource(viewModelScope, movieApi)
            }
        }

        return LivePagedListBuilder<Int, Movie>(dataSourceFactory, config)
    }
}