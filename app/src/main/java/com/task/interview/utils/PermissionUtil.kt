package com.task.interview.utils

import android.Manifest
import android.os.Build
import com.task.interview.App
import com.task.interview.di.inject


val app: App by inject()

fun locationPermissionArray(): Array<String> {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    ) else arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
}
