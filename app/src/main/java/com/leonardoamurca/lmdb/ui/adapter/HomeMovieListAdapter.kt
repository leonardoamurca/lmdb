package com.leonardoamurca.lmdb.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leonardoamurca.lmdb.databinding.HomeMovieItemBinding
import com.leonardoamurca.lmdb.model.Movie

class HomeMovieListAdapter(
    private val children: List<Movie>,
    private val onClick: (Movie) -> Unit
) :
    RecyclerView.Adapter<HomeMovieListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HomeMovieItemBinding.inflate(layoutInflater)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val child = children[position]

        holder.binding.movie = child
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = children.size

    inner class ViewHolder(val binding: HomeMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onClick(binding.movie!!)
        }
    }
}