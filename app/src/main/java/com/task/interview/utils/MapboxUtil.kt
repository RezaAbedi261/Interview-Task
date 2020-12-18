package com.task.interview.utils

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import androidx.core.content.res.ResourcesCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.task.interview.R
import com.task.interview.data.local.prefs.AppPreferences
import com.task.interview.di.inject
import java.util.*

object MapboxUtil {

    val prefs: AppPreferences by inject()

    @Keep
    fun getId(@DrawableRes drawable: Int): String {
        return String.format(Locale.US, "android_drawable_res_%d", drawable)
    }

    @Keep
    class MapboxIcon constructor(val id: String, val drawable: Drawable) {
        constructor(@DrawableRes drawable: Int, isVector: Boolean) : this(
            getId(drawable),
            getDrawable(drawable, isVector)
        ) {
        }

        companion object {
            private fun getDrawable(@DrawableRes drawable: Int, isVector: Boolean): Drawable {
                val app: Application by inject()
                return if (isVector) VectorDrawableCompat.create(
                    app.resources,
                    drawable,
                    app.theme
                )!! else ResourcesCompat.getDrawable(app.resources, drawable, app.theme)!!
            }
        }

        init {
            icons.add(this)
        }
    }

    @Keep
    var icons = ArrayList<MapboxIcon>(8)

    @Keep
    var pin = MapboxIcon(R.drawable.mapbox_marker_icon_default, false)


    private fun distance(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Double {
        val theta = lon1 - lon2
        var dist = (Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + (Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta))))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60 * 1.1515
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }
}