package com.task.interview.base


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.interview.data.local.prefs.AppPreferences
import com.task.interview.data.remote.AppApi
import com.task.interview.di.inject


abstract class BaseViewModel : ViewModel(){

    val prefs : AppPreferences by inject()
    val api : AppApi by inject()
    val connectivity = MutableLiveData<Boolean>()


}
