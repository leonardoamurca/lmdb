package com.leonardoamurca.lmdb.ui.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.leonardoamurca.lmdb.R
import com.leonardoamurca.lmdb.databinding.FragmentTrendingMoviesBinding
import com.leonardoamurca.lmdb.utils.GridSpacingItemDecoration
import com.leonardoamurca.lmdb.ui.adapter.MoviesAdapter
import org.koin.android.ext.android.inject

class TrendingMoviesFragment : Fragment() {

    private lateinit var databinding: FragmentTrendingMoviesBinding

    private val viewModel: TrendingMoviesViewModel by inject()

    private lateinit var movieListAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = DataBindingUtil.inflate<FragmentTrendingMoviesBinding>(
            inflater,
            R.layout.fragment_trending_movies,
            container,
            false
        ).apply {
            viewmodel = this@TrendingMoviesFragment.viewModel
            lifecycleOwner = this@TrendingMoviesFragment
        }

        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeList()
        setupObservers()
    }

    private fun initializeList() {
        movieListAdapter = MoviesAdapter(viewModel::showSelectedMovie)
        databinding.adapter = movieListAdapter
        databinding.movieListRecyclerView.apply {
            layoutManager = GridLayoutManager(
                activity,
                SPAN_COUNT,
                GridLayoutManager.VERTICAL,
                false
            )
            addItemDecoration(
                GridSpacingItemDecoration(
                    SPAN_COUNT,
                    SPACING,
                    INCLUDE_EDGE
                )
            )
        }
    }

    private fun setupObservers() {
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            it.let(movieListAdapter::submitList)
        })
    }

    companion object {
        private const val SPAN_COUNT = 3
        private const val SPACING = 30
        private const val INCLUDE_EDGE = true
    }
}