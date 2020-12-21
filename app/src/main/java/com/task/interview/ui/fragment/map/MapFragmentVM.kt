package com.task.interview.ui.fragment.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import com.github.musichin.reactivelivedata.combineLatestWith
import com.task.interview.base.BaseViewModel
import com.task.interview.data.repository.LocationsRepository
import com.task.interview.model.PlaceInfo


class MapFragmentVM : BaseViewModel() {

    val mapReady = MutableLiveData<Boolean>()
    val locationsResponse = LocationsRepository.locations
    val locations: LiveData<ArrayList<PlaceInfo>> =
        locationsResponse.combineLatestWith(mapReady).distinctUntilChanged().map {
            it.first
        }

}