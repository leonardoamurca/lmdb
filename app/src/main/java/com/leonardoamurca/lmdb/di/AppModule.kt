package com.leonardoamurca.lmdb.di

import com.leonardoamurca.lmdb.BuildConfig
import com.leonardoamurca.lmdb.db.AppDatabase
import com.leonardoamurca.lmdb.db.MovieRepository
import com.leonardoamurca.lmdb.db.MovieRepositoryImpl
import com.leonardoamurca.lmdb.network.AuthInterceptor
import com.leonardoamurca.lmdb.network.MovieApi
import com.leonardoamurca.lmdb.viewmodel.MovieViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    single { AppDatabase.buildDatabase(androidContext()) }
    single { get<AppDatabase>().movieDao() }
    single<MovieRepository> { MovieRepositoryImpl(get()) }
    single { AuthInterceptor() }
    single { OkHttpClient().newBuilder().addInterceptor(get<AuthInterceptor>()).build() }
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(get())
            .build()
    }
    single { get<Retrofit>().create(MovieApi::class.java) }
    viewModel { MovieViewModel(get(), get()) }
}