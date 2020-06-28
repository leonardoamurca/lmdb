package com.leonardoamurca.lmdb.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.leonardoamurca.lmdb.utils.ioThread


@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "app_database.db")
                .addCallback(populateDatabase(context))
                .build()

        private fun populateDatabase(context: Context): Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                ioThread {
                    getInstance(context)
                        .movieDao()
                        .insert(
                            MovieEntity(
                                1,
                                "Midsommar",
                                "A horror film",
                                7.1F,
                                "imagelink.jpg"
                            )
                        )
                }
            }
        }
    }
}