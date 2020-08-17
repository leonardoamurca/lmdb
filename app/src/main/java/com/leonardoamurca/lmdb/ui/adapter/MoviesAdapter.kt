package com.leonardoamurca.lmdb.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.leonardoamurca.lmdb.databinding.MovieItemBinding
import com.leonardoamurca.lmdb.model.Movie

class MoviesAdapter(private val onClick: (Movie) -> Unit) :
    PagedListAdapter<Movie, MoviesAdapter.ViewHolder>(
        Callback
    ) {

    class ViewHolder(val binding: MovieItemBinding, val onClick: (Movie) -> Unit) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onClick(binding.movie!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(layoutInflater)

        return ViewHolder(
            binding,
            onClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)

        holder.binding.movie = movie
        holder.binding.executePendingBindings()
    }

    companion object Callback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }
}

