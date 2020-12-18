package com.task.interview.utils

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayout
import com.task.interview.R


inline fun TabLayout.changeTabsFont(){
    val vg = this.getChildAt(0) as ViewGroup
    val tabsCount = vg.childCount
    for (j in 0 until tabsCount) {
        val vgTab = vg.getChildAt(j) as ViewGroup
        val tabChildrenCount = vgTab.childCount
        for (i in 0 until tabChildrenCount) {
            val tabViewChild = vgTab.getChildAt(i)
            if (tabViewChild is TextView) {
                tabViewChild.typeface = ResourcesCompat.getFont(vg.context, R.font.iran_sans_medium)
            }
        }
    }
}


fun View.observeGlobalLayoutOnce(observer: ()->Unit) =
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        override fun onGlobalLayout() {
            try {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                observer()
            } catch (ignored: Exception) {
            }
        }
    })

fun Toast.showToast(@DrawableRes background:Int=R.color.colorAccent, @ColorInt textColor:Int=Color.WHITE){
    val view: View? = this.view
    val text = view?.findViewById<View>(android.R.id.message) as TextView
    if (view.context != null) {
        text.typeface = ResourcesCompat.getFont(view.context, R.font.iran_sans_medium)
    }
    val startAndEndPadding = PixelUtil.dpToPx(16f)
    val topAndBottomPadding = PixelUtil.dpToPx(2f)
    text.setPadding(startAndEndPadding,topAndBottomPadding,startAndEndPadding,topAndBottomPadding)
    text.setTextColor(textColor)
    view.setBackgroundResource(background)
    this.show()
}

// Calculate the amount of time needed to read the message
fun CharSequence.getTimeNeededToRead(): Int {
    // Readable words per minute
    val wordPerMinutes = 180
    val minuteInMillis = 60 * 1000

    // Standardized number of chars in calculable word
    val wordLength = 5
    val wordCount = this.length / wordLength
    val readTime = (wordCount / wordPerMinutes) * minuteInMillis

    // Milliseconds before user starts reading the notification
    val startDelay = 1500

    // Extra time
    val endDelay = 1300

    return startDelay + readTime + endDelay
}
