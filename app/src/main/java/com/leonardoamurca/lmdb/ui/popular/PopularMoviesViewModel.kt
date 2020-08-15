package com.leonardoamurca.lmdb.ui.popular

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.leonardoamurca.lmdb.model.Movie
import com.leonardoamurca.lmdb.navigation.PopularMoviesCoordinator
import com.leonardoamurca.lmdb.network.api.MovieApi
import com.leonardoamurca.lmdb.ui.MoviesDataSource
import com.leonardoamurca.lmdb.ui.MoviesDataSourceFactory

class PopularMoviesViewModel(
    app: Application,
    movieApi: MovieApi,
    private val coordinator: PopularMoviesCoordinator
) : AndroidViewModel(app) {

    var movies: LiveData<PagedList<Movie>>

    private val moviesDataSourceFactory: MoviesDataSourceFactory =
        MoviesDataSourceFactory(
            PopularMoviesDataSource(
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

    fun closePopularMovies() {
        coordinator.closePopularMovies()
    }
}