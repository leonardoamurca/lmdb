package com.leonardoamurca.lmdb

import android.app.Application
import android.util.Log
import com.leonardoamurca.lmdb.db.AppDatabase
import com.leonardoamurca.lmdb.di.appModule
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(appModule)
        }
    }
}