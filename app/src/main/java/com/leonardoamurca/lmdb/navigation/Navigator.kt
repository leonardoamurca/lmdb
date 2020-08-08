package com.leonardoamurca.lmdb.navigation

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.leonardoamurca.lmdb.R
import com.leonardoamurca.lmdb.model.Movie
import com.leonardoamurca.lmdb.ui.home.HomeFragment
import com.leonardoamurca.lmdb.ui.moviedetails.MovieDetailsFragment
import com.leonardoamurca.lmdb.ui.trending.TrendingMoviesFragment

class Navigator {
    var activity: FragmentActivity? = null

    fun showTrendingMoviesScreen() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, TrendingMoviesFragment())
            .addToBackStack("Home")
            .commit()
    }

    fun showHomeScreen() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, HomeFragment())
            .commit()
    }

    fun showMovieDetails(movie: Movie) {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, MovieDetailsFragment.newInstance(movie))
            .addToBackStack("TrendingMovies")
            .commit()
    }

    fun closeTrendingMovies() {
        activity!!.supportFragmentManager
            .popBackStack()
    }
}