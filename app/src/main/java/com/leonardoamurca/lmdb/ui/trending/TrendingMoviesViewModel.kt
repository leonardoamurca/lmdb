package com.leonardoamurca.lmdb.ui.trending

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.leonardoamurca.lmdb.model.Movie
import com.leonardoamurca.lmdb.navigation.TrendingMoviesCoordinator
import com.leonardoamurca.lmdb.network.api.MovieApi

class TrendingMoviesViewModel(
    app: Application,
    movieApi: MovieApi,
    private val coordinator: TrendingMoviesCoordinator
) : AndroidViewModel(app) {

    var movies: LiveData<PagedList<Movie>>

    private val trendingMoviesDataSourceFactory: TrendingMoviesDataSourceFactory =
        TrendingMoviesDataSourceFactory(
            viewModelScope,
            movieApi
        )

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .build()

        movies = LivePagedListBuilder<Int, Movie>(trendingMoviesDataSourceFactory, config).build()
    }

    fun showLoading(): LiveData<Boolean> = Transformations.switchMap(
        trendingMoviesDataSourceFactory.dataSource,
        TrendingMoviesDataSource::loadingState
    )

    fun showSelectedMovie(movie: Movie) {
        coordinator.showSelectedMovieDetails(movie)
    }
}