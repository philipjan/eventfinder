package com.app.eventfinder.location

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.app.eventfinder.R
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap

class LocationUtil(
    private val ctx: Context,
    lifecycle: Lifecycle,
    private val mapBoxMap: MapboxMap
) : LifecycleObserver {
    private val TAG = this::class.java.simpleName

    init {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun startCheck() {
        initializeMap(true)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun stopCheck() {
        initializeMap(false)
    }

    @SuppressLint("MissingPermission")
    private fun initializeMap(checkLocation: Boolean) {
        // Create and customize the LocationComponent's options
        val customLocationComponentOptions = LocationComponentOptions.builder(ctx)
            .trackingGesturesManagement(true)
            .accuracyColor(ContextCompat.getColor(ctx, R.color.design_default_color_on_secondary))
            .build()

        val locationComponentActivationOptions = LocationComponentActivationOptions.builder(ctx, mapBoxMap.style!!)
            .locationComponentOptions(customLocationComponentOptions)
            .build()

        mapBoxMap.locationComponent.apply {
            activateLocationComponent(locationComponentActivationOptions)
            isLocationComponentEnabled = checkLocation
            cameraMode = CameraMode.TRACKING
            renderMode = RenderMode.COMPASS
        }
    }
}