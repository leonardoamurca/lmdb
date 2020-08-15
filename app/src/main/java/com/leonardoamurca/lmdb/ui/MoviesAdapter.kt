package com.leonardoamurca.lmdb.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.leonardoamurca.lmdb.databinding.TrendingMovieItemBinding
import com.leonardoamurca.lmdb.model.Movie

class MoviesAdapter(private val click: (Movie) -> Unit) :
    PagedListAdapter<Movie, MoviesAdapter.ViewHolder>(
        Companion
    ) {

    class ViewHolder(val binding: TrendingMovieItemBinding, val click: (Movie) -> Unit) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            click(binding.movie!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TrendingMovieItemBinding.inflate(layoutInflater)

        return ViewHolder(
            binding,
            click
        )
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
    }
}

