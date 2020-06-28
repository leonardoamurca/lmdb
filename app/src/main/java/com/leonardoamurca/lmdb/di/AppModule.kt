package com.leonardoamurca.lmdb.di

import com.leonardoamurca.lmdb.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { AppDatabase.buildDatabase(androidContext()) }
    single { get<AppDatabase>().movieDao() }
}