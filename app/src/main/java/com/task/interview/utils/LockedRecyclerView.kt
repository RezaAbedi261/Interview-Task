package com.task.interview.utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.Interpolator
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView

class LockedRecyclerView : RecyclerView {

    constructor(context: Context) : super(context){
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
    }

    var isLocked = true


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        //return super.fling(velocityX, velocityY)
        return false
    }

    override fun smoothScrollBy(dx: Int, dy: Int, interpolator: Interpolator?, duration: Int) {
        if(!isLocked)
            super.smoothScrollBy(dx, dy, interpolator, duration)
    }

    fun forceSmoothScrollToPosition(i: Int) {
        isLocked = false
        smoothScrollToPosition(i)
        isLocked = true
    }
}