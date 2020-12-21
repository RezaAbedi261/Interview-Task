package com.task.interview.ui.fragment.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import com.task.interview.base.BaseViewModel
import com.task.interview.data.repository.LocationsRepository
import com.task.interview.model.PlaceInfo


class MapFragmentVM : BaseViewModel() {

    val locationsResponse = LocationsRepository.locations
    val locations: LiveData<ArrayList<PlaceInfo>> =
        locationsResponse.distinctUntilChanged().map {
            it
        }

}