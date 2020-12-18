package com.task.interview

import android.app.Application
import androidx.annotation.UiThread
import com.task.interview.di.appModule
import com.task.interview.di.mapModule
import com.task.interview.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

var didInitKoin = false

@Synchronized
@UiThread
fun initKoin(application: Application?){

    if(!didInitKoin && application != null) {
        didInitKoin = true

        startKoin {
            // declare used Android context
            androidContext(application)
            // declare modules
            modules(listOf(appModule, networkModule,mapModule))
        }

    }
}
