package com.task.interview.utils

import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

import com.task.interview.R

class UiHelper {

    companion object {

        fun makeSnackBar(containerLayout: View, message: String, @BaseTransientBottomBar.Duration duration: Int = 1, minHeight: Int = -1): Snackbar {
            val context = containerLayout.context

            val snackbar = Snackbar.make(containerLayout, "", duration)
            val view = snackbar.view
            val layout = snackbar.view as Snackbar.SnackbarLayout
            val textView = layout.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
            textView.visibility = View.INVISIBLE
            layout.setPadding(0, 0, 0, 0)

            if (layout.layoutParams is ViewGroup.MarginLayoutParams) {
                val lp = layout.layoutParams as ViewGroup.MarginLayoutParams
                lp.setMargins(0, 0, 0, 0)
                lp.marginStart = 0
                lp.marginEnd = 0
                layout.layoutParams = lp
            }
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val snackView = inflater.inflate(R.layout.snackbar, if (containerLayout is ViewGroup) containerLayout else null, false)
            val textViewTop = snackView.findViewById<View>(R.id.tv_message) as TextView
            textViewTop.text = message
            if (minHeight > 0) {
                snackView.minimumHeight = minHeight
            }
            layout.addView(snackView, 0)
            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.marginStart=0
            params.marginEnd=0
            params.gravity = Gravity.TOP
            view.layoutParams = params
            return snackbar
        }


        fun showErrorSnackbar(containerLayout: View, message: String, @BaseTransientBottomBar.Duration duration: Int = 2 , minHeight: Int = -1): Snackbar {
            val duration= message.getTimeNeededToRead()
            val snackBar = makeSnackBar(containerLayout,message,duration,minHeight)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                snackBar.view.z = 20000f
            }

            snackBar.view.bringToFront()

            snackBar.view.observeGlobalLayoutOnce {
                (snackBar.view.parent as? ViewGroup)?.run {
                    bringChildToFront(snackBar.view)
                    requestLayout()
                    invalidate()
                }
            }

            snackBar.show()
            return snackBar
        }
    }





}