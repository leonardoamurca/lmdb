package com.leonardoamurca.lmdb.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.leonardoamurca.lmdb.R
import com.leonardoamurca.lmdb.databinding.FragmentMoviedetailsBinding
import com.leonardoamurca.lmdb.model.Movie
import org.koin.android.ext.android.inject

class MovieDetailsFragment : Fragment() {

    private val detailsViewModel: MovieDetailsViewModel by inject()

    private lateinit var databinding: FragmentMoviedetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = DataBindingUtil.inflate<FragmentMoviedetailsBinding>(
            inflater,
            R.layout.fragment_moviedetails,
            container,
            false
        ).apply {
            viewmodel = this@MovieDetailsFragment.detailsViewModel
            lifecycleOwner = this@MovieDetailsFragment
        }

        arguments?.getParcelable<Movie>(MOVIE_KEY).let { movie ->
            detailsViewModel.init(movie)
        }

        return databinding.root
    }

    companion object {
        private const val MOVIE_KEY = "MOVIE"

        fun newInstance(movie: Movie): MovieDetailsFragment {
            val bundle = bundleOf(
                MOVIE_KEY to movie
            )

            val fragment = MovieDetailsFragment()
            fragment.arguments = bundle

            return fragment
        }
    }
}
