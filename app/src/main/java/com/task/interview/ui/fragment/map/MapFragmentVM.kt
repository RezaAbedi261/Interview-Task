package com.task.interview.ui.fragment.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import com.github.musichin.reactivelivedata.combineLatestWith
import com.task.interview.base.BaseViewModel
import com.task.interview.model.PlaceInfo
import com.task.interview.model.Places
import com.task.interview.utils.testModel

class MapFragmentVM : BaseViewModel() {

    val mapReady = MutableLiveData<Boolean>()
    val locationsResponse = MutableLiveData<Places>()
    val locations: LiveData<ArrayList<PlaceInfo>> =
        locationsResponse.combineLatestWith(mapReady).distinctUntilChanged().map {
            it.first.places
        }

    fun getLocations() {
        locationsResponse.value = testModel()
    }

}