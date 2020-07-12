package com.leonardoamurca.lmdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.leonardoamurca.lmdb.databinding.ActivityMovieBinding
import com.leonardoamurca.lmdb.network.Movie
import com.leonardoamurca.lmdb.viewmodel.MovieViewModel
import org.koin.android.ext.android.inject

class MovieActivity : AppCompatActivity() {

    private val viewModel: MovieViewModel by inject()

    private lateinit var databinding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView<ActivityMovieBinding>(
            this,
            R.layout.activity_movie
        ).apply {
            viewmodel = this@MovieActivity.viewModel
            lifecycleOwner = this@MovieActivity
        }

        val movie = intent.getParcelableExtra<Movie>(MOVIE_KEY)

        viewModel.init(movie)
    }

    companion object {
        private const val MOVIE_KEY = "MOVIE"
    }
}
