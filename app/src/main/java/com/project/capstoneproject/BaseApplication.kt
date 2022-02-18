package com.project.capstoneproject

import android.app.Application
import com.project.capstoneproject.di.useCaseModule
import com.project.capstoneproject.di.viewModelModule
import com.project.core.di.databaseModule
import com.project.core.di.networkModule
import com.project.core.di.repositoryModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@ExperimentalCoroutinesApi
class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BaseApplication)
            modules(
                databaseModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}