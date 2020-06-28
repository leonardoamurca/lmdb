package com.leonardoamurca.lmdb.di

import com.leonardoamurca.lmdb.db.AppDatabase
import com.leonardoamurca.lmdb.db.MovieRepository
import com.leonardoamurca.lmdb.db.MovieRepositoryImpl
import com.leonardoamurca.lmdb.viewmodel.MovieViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AppDatabase.buildDatabase(androidContext()) }
    single { get<AppDatabase>().movieDao() }
    single<MovieRepository> { MovieRepositoryImpl(get()) }
    viewModel { MovieViewModel(get(), get()) }
}