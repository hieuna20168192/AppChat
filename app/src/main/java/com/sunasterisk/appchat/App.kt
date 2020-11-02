package com.sunasterisk.appchat

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import com.sunasterisk.appchat.di.repositoryModule
import com.sunasterisk.appchat.di.viewModelModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        startKoin {
            androidContext(this@App)
            modules(repositoryModule)
            modules(viewModelModule)
        }
    }

    override fun getApplicationContext(): Context {
        instance = this
        return super.getApplicationContext()
    }

    companion object {
        lateinit var instance: App
        fun getContext() = instance.applicationContext
    }
}
