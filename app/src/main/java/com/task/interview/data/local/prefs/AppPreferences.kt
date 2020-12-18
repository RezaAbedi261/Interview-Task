package com.task.interview.data.local.prefs

import android.content.Context
import com.task.interview.utils.LocaleHelper

class AppPreferences(context: Context) : PrefsWrapper(context) {

    var language by intPref(LocaleHelper.LOCALE_PERSIAN)


}