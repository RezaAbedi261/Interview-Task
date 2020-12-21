package com.task.interview.ui.fragment.map

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.viewpager2.widget.ViewPager2
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponent
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.Symbol
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions
import com.mapbox.mapboxsdk.utils.ColorUtils
import com.task.interview.R
import com.task.interview.base.BaseFragment
import com.task.interview.databinding.FragmentMapBinding
import com.task.interview.model.PlaceInfo
import com.task.interview.utils.MapboxUtil
import com.task.interview.utils.NavigationAnimations
import com.task.interview.utils.ZOOM_LEVEL
import kotlinx.android.synthetic.main.fragment_map.*
import org.koin.android.ext.android.inject
import kotlin.math.abs


class MapFragment : BaseFragment<FragmentMapBinding, MapFragmentVM>(),
    MapboxMap.OnMapClickListener, OnMapReadyCallback, LocationItemListener {


    override val viewModel: MapFragmentVM by viewModels()
    override fun layout() = R.layout.fragment_map;

    val mapStyle: Style.Builder by inject()
    private var mapboxMap: MapboxMap? = null
    private var locationComponent: LocationComponent? = null
    private var permissionsManager: PermissionsManager? = null
    var adapter: LocationListAdapter? = null
    var symbolManager: SymbolManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(app, getString(R.string.mapbox_access_token))

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mapView.onCreate(savedInstanceState);
        mapView?.getMapAsync(this)

        initLocationViewPager()
        viewModel.getLocations()
    }

    private fun initLocationViewPager() {

        adapter = LocationListAdapter(context, this)
        locationsViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        locationsViewPager.adapter = adapter
        locationsViewPager.offscreenPageLimit = 1


        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = +pageTranslationX * position
            page.scaleY = 1 - (0.25f * abs(position))
        }
        locationsViewPager.setPageTransformer(pageTransformer)

        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        locationsViewPager.addItemDecoration(itemDecoration)

        locationsViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.locations.value?.get(position)?.let { moveCamera(it) }

            }
        })
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapboxMap = mapboxMap


        mapboxMap.setStyle(mapStyle) { style ->
            enableLocationComponent(style)
            mapboxMap.addOnMapClickListener(this)
            val geoJsonOptions: GeoJsonOptions = GeoJsonOptions().withTolerance(0.4f)

            symbolManager = SymbolManager(mapView, mapboxMap, style, null, geoJsonOptions)
            symbolManager?.iconAllowOverlap = true
            symbolManager?.iconIgnorePlacement = true
            symbolManager?.addClickListener { symbol: Symbol ->
                Toast.makeText(
                    context, String.format("Symbol clicked %s", symbol.id),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }

            viewModel.mapReady.value = true
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
        observe(viewModel.locations) { locations ->
            if (!locations.isNullOrEmpty()) {
                adapter?.addItems(locations)
                locations.forEach {
                    addMarker(it)
                }
                moveCamera(locations[0])
            }
        }
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

    fun addMarker(placeInfo: PlaceInfo) {
        symbolManager?.create(
            SymbolOptions()
                .withLatLng(LatLng(placeInfo.lat, placeInfo.lng))
                .withIconImage(MapboxUtil.pin.id)
                .withIconColor(ColorUtils.colorToRgbaString(Color.YELLOW))
                .withIconSize(1.0f)
                .withSymbolSortKey(5.0f)
                .withDraggable(true)
        )
    }

    private fun moveCamera(placeInfo: PlaceInfo) {
        placeInfo.let {
            val position = CameraPosition.Builder()
                .target(
                    LatLng(
                        it.lat,
                        it.lng
                    )
                )
                .zoom(ZOOM_LEVEL)
                .bearing(180.0)
                .tilt(30.0)
                .build()

            mapboxMap?.animateCamera(
                CameraUpdateFactory
                    .newCameraPosition(position), 2000
            )
        }
    }

    override fun onClick(item: PlaceInfo, imageView: ImageView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.transitionName = "imageView"
            val extras = FragmentNavigatorExtras(
                imageView to "imageView"
            )
            navigate(MapFragmentDirections.actionMapToDetail(item), extras)
        } else {
            navigate(
                MapFragmentDirections.actionMapToDetail(item),
                NavigationAnimations.leftToRight
            )

        }


    }
}