package com.project.core.di

import androidx.room.Room
import com.project.core.data.source.local.MovieLocalDataSource
import com.project.core.data.source.local.MovieDatabase
import com.project.core.data.source.local.TvShowLocalDataSource
import com.project.core.data.source.remote.MovieRemoteDataSource
import com.project.core.data.source.remote.TvShowRemoteDataSource
import com.project.core.data.source.remote.network.ApiService
import com.project.core.data.source.repository.MovieRepository
import com.project.core.data.source.repository.TvShowRepository
import com.project.core.domain.repository.IMovieRepository
import com.project.core.domain.repository.ITvShowRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MovieDatabase>().movieDao() }
    factory { get<MovieDatabase>().tvShowDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "movie.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(ApiService::class.java)
    }
}

@ExperimentalCoroutinesApi
val repositoryModule = module {
    single { MovieLocalDataSource(get()) }
    single { MovieRemoteDataSource(get()) }
    single { TvShowLocalDataSource(get()) }
    single { TvShowRemoteDataSource(get()) }
    single<IMovieRepository> {
        MovieRepository(
            get(),
            get()
        )
    }
    single<ITvShowRepository> {
        TvShowRepository(
            get(),
            get()
        )
    }
}