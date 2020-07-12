package com.leonardoamurca.lmdb

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leonardoamurca.lmdb.databinding.MovieItemBinding
import com.leonardoamurca.lmdb.network.Movie

class MovieListAdapter :
    ListAdapter<Movie, MovieListAdapter.ViewHolder>(Companion) {

    class ViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val context = binding.root.context
            val showMovieDetailsIntent = Intent(context, MovieActivity::class.java)

            showMovieDetailsIntent.putExtra(MOVIE_KEY, binding.movie)
            context.startActivity(showMovieDetailsIntent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(layoutInflater)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)

        holder.binding.movie = movie
        holder.binding.executePendingBindings()
    }

    companion object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        private const val MOVIE_KEY = "MOVIE"
    }
}

