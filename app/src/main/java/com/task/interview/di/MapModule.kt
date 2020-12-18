package com.task.interview.di

import android.app.Application
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.Style
import com.task.interview.R
import com.task.interview.utils.MapboxUtil
import com.task.interview.utils.TimeUtil
import org.koin.dsl.module

val mapModule = module {
    factory {
        val ans = Style.Builder().fromUri(
            get<Application>().getString(
                if (TimeUtil.isNight)
                    R.string.parsimap_dark
                else
                    R.string.parsimap_street
            )
        )
        for (icon in MapboxUtil.icons) {
            ans.withImage(icon.id, icon.drawable)
        }
        ans
    }

    single {
        Mapbox.getInstance(
            get<Application>(),
            get<Application>().getString(R.string.mapbox_access_token)
        )
    }

}