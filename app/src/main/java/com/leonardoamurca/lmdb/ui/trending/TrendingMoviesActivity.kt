package com.leonardoamurca.lmdb.ui.trending

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.leonardoamurca.lmdb.R
import com.leonardoamurca.lmdb.databinding.ActivityTrendingMoviesBinding
import org.koin.android.ext.android.inject

class TrendingMoviesActivity : AppCompatActivity() {

    private lateinit var databinding: ActivityTrendingMoviesBinding

    private val viewModel: TrendingMoviesViewModel by inject()

    private val movieListAdapter = TrendingMoviesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databinding = DataBindingUtil.setContentView<ActivityTrendingMoviesBinding>(
            this,
            R.layout.activity_trending_movies
        ).apply {
            viewmodel = this@TrendingMoviesActivity.viewModel
            lifecycleOwner = this@TrendingMoviesActivity
        }

        initializeList()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.movies.observe(this, Observer {
            it.let(movieListAdapter::submitList)
        })
    }

    private fun initializeList() {
        databinding.adapter = movieListAdapter
        databinding.movieListRecyclerView.layoutManager = LinearLayoutManager(
            this@TrendingMoviesActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
    }
}