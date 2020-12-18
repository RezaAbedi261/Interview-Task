package com.task.interview

import android.app.Application
import com.task.interview.di.inject

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(this)

    }

    companion object {
        val context: App by inject()
    }
}
