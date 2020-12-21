package com.task.interview.ui.fragment.splash

import com.task.interview.base.BaseViewModel
import com.task.interview.data.repository.LocationsRepository

class SplashFragmentVM : BaseViewModel() {

    fun getLocations() {
        LocationsRepository.getLocations()
    }

}