package com.task.interview.ui.fragment.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponent
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.task.interview.R
import com.task.interview.base.BaseFragment
import com.task.interview.databinding.FragmentMapBinding
import com.task.interview.model.Places
import kotlinx.android.synthetic.main.fragment_map.*
import org.koin.android.ext.android.inject


class MapFragment : BaseFragment<FragmentMapBinding, MapFragmentVM>(),
    MapboxMap.OnMapClickListener, OnMapReadyCallback {


    override val viewModel: MapFragmentVM by viewModels()
    lateinit var locationInfo: Places
    private val SOURCE_ID = "SOURCE_ID"
    private val ICON_ID = "ICON_ID"
    private val LAYER_ID = "LAYER_ID"
    override fun layout() = R.layout.fragment_map;

    val mapStyle: Style.Builder by inject()
    private var mapboxMap: MapboxMap? = null
    private var locationComponent: LocationComponent? = null
    private var permissionsManager: PermissionsManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(app, getString(R.string.mapbox_access_token))

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        mapView.onCreate(savedInstanceState);
        mapView?.getMapAsync(this)


    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapboxMap = mapboxMap

        addMarker()
        mapboxMap.setStyle(mapStyle) { style ->
            enableLocationComponent(style)
            mapboxMap.addOnMapClickListener(this)
            moveCamera()
        }
    }


    private fun enableLocationComponent(loadedMapStyle: Style) {
        if (PermissionsManager.areLocationPermissionsGranted(requireActivity())) {

            locationComponent = mapboxMap?.locationComponent
            locationComponent?.activateLocationComponent(requireContext(), loadedMapStyle)

            // Set the component's camera mode
            locationComponent?.cameraMode = CameraMode.TRACKING
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissionsManager?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun liveDataObservers() {
    }


    // Add the mapView lifecycle to the activity's lifecycle methods
    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState);
    }

    override fun onMapClick(point: LatLng): Boolean {
        return false
    }

    fun addMarker() {

//        locationInfo?.let {
//            if (locationInfo.location != null && locationInfo.location?.lat != null && locationInfo.location?.lng != null) {
//                mapStyle.withImage(
//                    ICON_ID, BitmapFactory.decodeResource(
//                        resources, R.drawable.mapbox_marker_icon_default
//                    )
//                )
//                    .withSource(
//                        GeoJsonSource(
//                            SOURCE_ID, FeatureCollection.fromFeature(
//                                Feature.fromGeometry(
//                                    Point.fromLngLat(
//                                        locationInfo.location?.lng!!,
//                                        locationInfo.location?.lat!!
//                                    )
//                                )
//                            )
//                        )
//                    )
//                    .withLayer(
//                        SymbolLayer(LAYER_ID, SOURCE_ID)
//                            .withProperties(
//                                PropertyFactory.iconImage(ICON_ID),
//                                PropertyFactory.iconAllowOverlap(true),
//                                PropertyFactory.iconIgnorePlacement(true)
//                            )
//                    )
//
//            }
//
//            binding.tvAddress.setRightText(locationInfo.location?.country + locationInfo.location?.city + locationInfo.location?.address)
//            locationInfo.name?.let { it1 -> binding.tvName.setRightText(it1) }
//            if (locationInfo?.categories.isNotEmpty())
//                locationInfo?.categories[0].name?.let { it1 -> binding.tvCategory.setRightText(it1) }
//        }
    }

    private fun moveCamera() {
//        locationInfo?.let {
//            if (locationInfo.location != null && locationInfo.location?.lat != null && locationInfo.location?.lng != null) {
//                val position = CameraPosition.Builder()
//                    .target(
//                        LatLng(
//                            locationInfo.location?.lat!!,
//                            locationInfo.location?.lng!!
//                        )
//                    )
//                    .zoom(ZOOM_LEVEL)
//                    .bearing(180.0)
//                    .tilt(30.0)
//                    .build()
//
//                mapboxMap?.animateCamera(
//                    CameraUpdateFactory
//                        .newCameraPosition(position), 2000
//                )
//            }
//        }
    }
}