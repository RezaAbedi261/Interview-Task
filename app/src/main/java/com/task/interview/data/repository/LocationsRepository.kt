package com.task.interview.data.repository

import androidx.lifecycle.MutableLiveData
import com.task.interview.data.local.db.LocationsDatabase
import com.task.interview.data.remote.apiCall
import com.task.interview.di.inject
import com.task.interview.model.PlaceInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object LocationsRepository : CoroutineScope by CoroutineScope(Dispatchers.Main) {

    val db: LocationsDatabase by inject()
    val dbLocationsCache = ArrayList<PlaceInfo>()
    val locations = MutableLiveData<ArrayList<PlaceInfo>>()

    fun getLocations() {

        launch {
            var response = apiCall {
                getPlaces()
            }
            val locationsResponse = response.response?.places

            if (!response.isError && !locationsResponse.isNullOrEmpty()) {

                CoroutineScope(Dispatchers.IO).launch {
                    db.getLocationsDbDao().deleteAll()
                    db.getLocationsDbDao().insertAll(locationsResponse)
                    dbLocationsCache.addAll(locationsResponse)
                    CoroutineScope(Dispatchers.Main).launch {
                        locations.value = locationsResponse
                    }

                }

            } else {
                if (dbLocationsCache.isEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        dbLocationsCache.addAll(db.getLocationsDbDao().getAll())
                        CoroutineScope(Dispatchers.Main).launch {
                            locations.value = dbLocationsCache
                        }
                    }
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        locations.value = dbLocationsCache
                    }
                }
            }
        }
    }

}