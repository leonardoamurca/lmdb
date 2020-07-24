package com.leonardoamurca.lmdb.di

import com.leonardoamurca.lmdb.BuildConfig
import com.leonardoamurca.lmdb.db.AppDatabase
import com.leonardoamurca.lmdb.db.repository.MovieRepository
import com.leonardoamurca.lmdb.db.repository.MovieRepositoryImpl
import com.leonardoamurca.lmdb.network.AuthInterceptor
import com.leonardoamurca.lmdb.network.api.MovieApi
import com.leonardoamurca.lmdb.ui.home.HomeViewModel
import com.leonardoamurca.lmdb.ui.trending.TrendingMoviesViewModel
import com.leonardoamurca.lmdb.ui.movie.MovieViewModel
import com.leonardoamurca.lmdb.ui.titlebar.TitleBarViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    single { AppDatabase.buildDatabase(androidContext()) }
    single { get<AppDatabase>().movieDao() }
    single<MovieRepository> {
        MovieRepositoryImpl(
            get()
        )
    }
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
    viewModel { MovieViewModel(get()) }
    viewModel {
        TrendingMoviesViewModel(
            get(),
            get()
        )
    }
    viewModel { TitleBarViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}