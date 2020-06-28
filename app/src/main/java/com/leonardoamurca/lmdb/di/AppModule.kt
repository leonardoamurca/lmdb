package com.leonardoamurca.lmdb.di

import com.leonardoamurca.lmdb.db.AppDatabase
import com.leonardoamurca.lmdb.db.MovieRepository
import com.leonardoamurca.lmdb.db.MovieRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { AppDatabase.buildDatabase(androidContext()) }
    single { get<AppDatabase>().movieDao() }
    single<MovieRepository> { MovieRepositoryImpl(get()) }
}