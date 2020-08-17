package com.leonardoamurca.lmdb.ui.trending

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.leonardoamurca.lmdb.model.Movie
import com.leonardoamurca.lmdb.navigation.TrendingMoviesCoordinator
import com.leonardoamurca.lmdb.network.api.MovieApi
import com.leonardoamurca.lmdb.ui.datasource.MoviesDataSource
import com.leonardoamurca.lmdb.ui.datasource.MoviesDataSourceFactory
import com.leonardoamurca.lmdb.ui.datasource.TrendingMoviesDataSource

class TrendingMoviesViewModel(
    app: Application,
    movieApi: MovieApi,
    private val coordinator: TrendingMoviesCoordinator
) : AndroidViewModel(app) {

    var movies: LiveData<PagedList<Movie>>

    private val moviesDataSourceFactory: MoviesDataSourceFactory =
        MoviesDataSourceFactory(
            TrendingMoviesDataSource(
                viewModelScope,
                movieApi
            )
        )

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

    fun showSelectedMovie(movie: Movie) {
        coordinator.showSelectedMovieDetails(movie)
    }

    fun closeTrendingMovies() {
        coordinator.closeTrendingMovies()
    }
}