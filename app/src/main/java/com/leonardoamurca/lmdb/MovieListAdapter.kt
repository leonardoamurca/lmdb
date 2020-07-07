package com.leonardoamurca.lmdb

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leonardoamurca.lmdb.network.MovieResponse
import com.leonardoamurca.lmdb.utils.bindImage
import com.leonardoamurca.lmdb.utils.inflate
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieListAdapter(private val movies: List<MovieResponse>) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.movie_item, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var movie: MovieResponse? = null

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: MovieResponse) {
            this.movie = movie

            itemView.titleItem.text = movie.title
            bindImage(itemView.posterImageItem, movie.posterPath)
        }

        override fun onClick(v: View?) {
            val context = itemView.context
            val showMovieDetailsIntent = Intent(context, MovieActivity::class.java)

            showMovieDetailsIntent.putExtra(MOVIE_KEY, movie?.id)
            context.startActivity(showMovieDetailsIntent)
        }

        companion object {
            private const val MOVIE_KEY = "MOVIE"
        }
    }

}

