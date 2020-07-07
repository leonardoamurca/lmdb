package com.leonardoamurca.lmdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.leonardoamurca.lmdb.network.MovieResponse
import kotlinx.android.synthetic.main.activity_movie_list.*

class MovieListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        movieListRecyclerView.apply {
            adapter = MovieListAdapter(movies())
            layoutManager =
                LinearLayoutManager(
                    this@MovieListActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
        }
    }

    private fun movies(): List<MovieResponse> {
        return listOf(
            MovieResponse(530385, "Midsommar", "a", "/7LEI8ulZzO5gy9Ww2NVCrKmHeDZ.jpg", 7.1F),
            MovieResponse(
                570670,
                "The Invisible Man",
                "a",
                "/7LEI8ulZzO5gy9Ww2NVCrKmHeDZ.jpg",
                8.1F
            ),
            MovieResponse(475557, "Joker", "a", "/7LEI8ulZzO5gy9Ww2NVCrKmHeDZ.jpg", 10.1F)
        )
    }
}