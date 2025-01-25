package com.tcivileva.nata.sekveniya.films.project

import android.app.Application
import com.tcivileva.nata.sekveniya.films.project.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class FilmApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@FilmApplication)
            modules(appModule)
        }
    }
}