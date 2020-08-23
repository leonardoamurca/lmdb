package com.leonardoamurca.lmdb.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.leonardoamurca.lmdb.model.Collection
import com.leonardoamurca.lmdb.model.Movie
import com.leonardoamurca.lmdb.navigation.HomeCoordinator
import com.leonardoamurca.lmdb.navigation.PopularMoviesCoordinator
import com.leonardoamurca.lmdb.navigation.TrendingMoviesCoordinator
import com.leonardoamurca.lmdb.network.api.MovieApi

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
            pageNumber = 1
        )

        val trendingMovies = movieApi.getTrendingMoviesOf("day", 1)

        if (popularMovies.isSuccessful && trendingMovies.isSuccessful) {
            collections.value =
                listOf(
                    Collection("Trending", trendingMovies.body()?.results),
                    Collection("Popular", popularMovies.body()?.results)
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
}