package com.leonardoamurca.lmdb.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = MovieEntity.TABLE_NAME)
data class MovieEntity(
    @ColumnInfo(name = FIELD_ID)
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = FIELD_TITLE)
    val title: String,
    @ColumnInfo(name = FIELD_OVERVIEW)
    val overview: String,
    @ColumnInfo(name = FIELD_VOTE_AVERAGE)
    val voteAverage: Float,
    @ColumnInfo(name = FIELD_POSTER_IMAGE)
    val posterPath: String
) {

    companion object {
        const val TABLE_NAME = "movies"
        private const val FIELD_ID = "id"
        private const val FIELD_TITLE = "title"
        private const val FIELD_VOTE_AVERAGE = "vote_average"
        private const val FIELD_OVERVIEW = "overview"
        private const val FIELD_POSTER_IMAGE = "poster_path"
    }
}