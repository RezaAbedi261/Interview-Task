package com.task.interview.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.LinearLayout
import com.task.interview.R
import kotlinx.android.synthetic.main.dialog_loading.*

class LoadingDialog(context: Context?) : Dialog(context!!, R.style.Theme_Dialog) {

    var loadingListener: LoadingListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val window: Window? = window
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        requestWindowFeature(Window.FEATURE_NO_TITLE);



        setContentView(R.layout.dialog_loading)




        setCancelable(false)
        setCanceledOnTouchOutside(false)

        btn_cancel.setOnClickListener {
            loadingListener?.loadingCancelClicked()
            dismiss()
        }
    }

}

interface LoadingListener {
    fun loadingCancelClicked()
}