package com.leonardoamurca.lmdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.leonardoamurca.lmdb.databinding.ActivityMovieListBinding
import com.leonardoamurca.lmdb.viewmodel.MovieListViewModel
import org.koin.android.ext.android.inject

class MovieListActivity : AppCompatActivity() {

    private lateinit var databinding: ActivityMovieListBinding

    private val viewModel: MovieListViewModel by inject()

    private val movieListAdapter = MovieListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databinding = DataBindingUtil.setContentView<ActivityMovieListBinding>(
            this,
            R.layout.activity_movie_list
        ).apply {
            viewmodel = this@MovieListActivity.viewModel
            lifecycleOwner = this@MovieListActivity
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
            this@MovieListActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
    }
}