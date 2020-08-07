package com.leonardoamurca.lmdb.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.leonardoamurca.lmdb.navigation.TrendingMoviesCoordinator

class HomeViewModel(
    private val coordinator: TrendingMoviesCoordinator,
    app: Application
) : AndroidViewModel(app) {

    fun navigateToTrendingMovies() {
        coordinator.start()
    }
}