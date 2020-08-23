package com.leonardoamurca.lmdb.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leonardoamurca.lmdb.R
import com.leonardoamurca.lmdb.model.Collection
import com.leonardoamurca.lmdb.model.Movie
import kotlinx.android.synthetic.main.collection.view.*
import kotlin.reflect.KFunction1

class CollectionAdapter(
    private val onMovieClick: (Movie) -> Unit,
    private val onCollectionClick: KFunction1<@ParameterName(name = "to") String, Unit?>
) :
    ListAdapter<Collection, CollectionAdapter.ViewHolder>(DiffCallback) {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        // TODO: Create an extension function for this inflation
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.collection,
                parent,
                false
            )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val collection = getItem(position)

        holder.bind(collection)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val collectionTitle: TextView = itemView.collectionTitle
        private val recyclerView: RecyclerView = itemView.homeMovieRecyclerView
        private lateinit var homeMovieListLayoutManager: LinearLayoutManager

        init {
            collectionTitle.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onCollectionClick(collectionTitle.text.toString())
        }

        fun bind(collection: Collection) {
            homeMovieListLayoutManager = LinearLayoutManager(
                recyclerView.context,
                LinearLayoutManager.HORIZONTAL,
                false
            ).apply {
                initialPrefetchItemCount = 4
            }

            collectionTitle.text = collection.title
            recyclerView.apply {
                layoutManager = homeMovieListLayoutManager
                adapter = collection.children?.let { HomeMovieListAdapter(it, onMovieClick) }
                setRecycledViewPool(viewPool)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Collection>() {
        override fun areItemsTheSame(oldItem: Collection, newItem: Collection): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Collection, newItem: Collection): Boolean {
            return oldItem.title == newItem.title
        }
    }
}