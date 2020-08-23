package com.leonardoamurca.lmdb.utils

import com.leonardoamurca.lmdb.model.Collection
import com.leonardoamurca.lmdb.model.Movie
import java.util.Random

object ParentDataFactory {
    private val random = Random()

    private val titles =
        arrayListOf("Now Playing", "Classic", "Comedy", "Thriller", "Action", "Horror", "TV Series")

    private fun randomTitle(): String {
        val index = random.nextInt(titles.size)
        return titles[index]
    }

    private fun randomChildren(): List<Movie> {
        return ChildDataFactory.getChildren(10)
    }

    fun getParents(count: Int): List<Collection> {
        val parents = mutableListOf<Collection>()
        repeat(count) {
            val parent = Collection(randomTitle(), randomChildren())
            parents.add(parent)
        }
        return parents
    }
}

object ChildDataFactory {

    fun getChildren(count: Int): List<Movie> {
        val children = mutableListOf<Movie>()
        repeat(count) {
            val child = Movie(
                1,
                "Midsommar",
                "Several friends travel to Sweden to study as anthropologists a summer festival that is held every ninety years in the remote hometown of one of them. What begins as a dream vacation in a place where the sun never sets, gradually turns into a dark nightmare as the mysterious inhabitants invite them to participate in their disturbing festive activities.",
                "/7LEI8ulZzO5gy9Ww2NVCrKmHeDZ.jpg",
                7.1F
            )
            children.add(child)
        }
        return children
    }


}