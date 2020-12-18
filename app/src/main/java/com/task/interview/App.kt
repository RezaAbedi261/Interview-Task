package com.task.interview

import android.app.Application
import com.task.interview.di.appModule
import com.task.interview.di.inject
import com.task.interview.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate(){
        super.onCreate()
//        // start Koin!
        startKoin {
            // declare used Android context
            androidContext(this@App)
            // declare modules
            modules(listOf(appModule, networkModule))
        }

    }

    companion object {
        val context: App by inject()
    }
}
