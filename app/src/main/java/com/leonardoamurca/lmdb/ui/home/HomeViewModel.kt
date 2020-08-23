package com.leonardoamurca.lmdb.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.leonardoamurca.lmdb.model.Collection
import com.leonardoamurca.lmdb.model.CollectionTypes
import com.leonardoamurca.lmdb.model.Movie
import com.leonardoamurca.lmdb.navigation.HomeCoordinator
import com.leonardoamurca.lmdb.navigation.PopularMoviesCoordinator
import com.leonardoamurca.lmdb.navigation.TrendingMoviesCoordinator
import com.leonardoamurca.lmdb.network.NetworkState
import com.leonardoamurca.lmdb.network.NetworkState.Success
import com.leonardoamurca.lmdb.network.api.MovieApi
import com.leonardoamurca.lmdb.utils.handleListStateResponse

class HomeViewModel(
    private val trendingCoordinator: TrendingMoviesCoordinator,
    private val popularCoordinator: PopularMoviesCoordinator,
    private val homeCoordinator: HomeCoordinator,
    private val movieApi: MovieApi,
    app: Application
) : AndroidViewModel(app) {

    var collections = MutableLiveData<List<Collection>>()

    suspend fun fetchCollections() {
        val popularMovies = movieApi.getPopularMovies(
            language = "en-US",
            sortBy = "popularity.ascdesc",
            includeAdult = false,
            includeVideo = false,
            pageNumber = FIRST_PAGE
        ).handleListStateResponse()

        val trendingMovies = movieApi.getTrendingMoviesOf(
            period = "day",
            pageNumber = FIRST_PAGE
        ).handleListStateResponse()

        setupCollections(trendingMovies, popularMovies)
    }

    private fun setupCollections(
        trendingMovies: NetworkState<List<Movie>>,
        popularMovies: NetworkState<List<Movie>>
    ) {
        if (trendingMovies is Success && popularMovies is Success) {
            collections.value =
                listOf(
                    Collection(CollectionTypes.TRENDING.value, trendingMovies.data),
                    Collection(CollectionTypes.POPULAR.name, popularMovies.data)
                )
        }
    }

    fun navigate(to: String) = when (to) {
        "Trending" -> navigateToTrendingMovies()
        "Popular" -> navigateToPopularMovies()
        else -> null
    }

    private fun navigateToTrendingMovies() {
        trendingCoordinator.start()
    }

    private fun navigateToPopularMovies() {
        popularCoordinator.start()
    }

    fun showSelectedMovie(movie: Movie) {
        homeCoordinator.showSelectedMovieDetails(movie)
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}