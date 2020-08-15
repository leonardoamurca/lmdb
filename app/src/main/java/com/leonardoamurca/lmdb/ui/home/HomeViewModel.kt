package com.leonardoamurca.lmdb.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.leonardoamurca.lmdb.navigation.PopularMoviesCoordinator
import com.leonardoamurca.lmdb.navigation.TrendingMoviesCoordinator

class HomeViewModel(
    private val trendingCoordinator: TrendingMoviesCoordinator,
    private val popularCoordinator: PopularMoviesCoordinator,
    app: Application
) : AndroidViewModel(app) {

    fun navigateToTrendingMovies() {
        trendingCoordinator.start()
    }

    fun navigateToPopularMovies() {
        popularCoordinator.start()
    }
}