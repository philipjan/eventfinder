package com.app.eventfinder

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.app.eventfinder.databinding.ActivityMainBinding
import com.app.eventfinder.location.LocationUtil
import com.app.eventfinder.util.PermissionManager
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style

class MainActivity :
    AppCompatActivity(),
    PermissionsListener,
    OnMapReadyCallback {

    private lateinit var binder: ActivityMainBinding
    private lateinit var locationListener: LocationUtil
    private lateinit var permissionManager: PermissionsManager
    private lateinit var mapBoxMap: MapboxMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token))
        binder = ActivityMainBinding.inflate(layoutInflater)
        with(binder.mapView) {
            this.onCreate(savedInstanceState)
            this.getMapAsync(this@MainActivity)
        }
        setContentView(binder.root)
    }

    override fun onStart() {
        super.onStart()
        binder.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binder.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binder.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binder.mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        binder.mapView.onSaveInstanceState(outState)
    }


    override fun onLowMemory() {
        super.onLowMemory()
        binder.mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        binder.mapView.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {

    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            locationListener = LocationUtil(
                this,
                lifecycle,
                this.mapBoxMap
            )
        } else {
            PermissionManager.showDeniedPermissionDialog(
                this
            ) {
                startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", this@MainActivity.packageName, null)
                })
            }
        }
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapBoxMap = mapboxMap
        this.mapBoxMap.setStyle(Style.MAPBOX_STREETS
        ) {
            checkNeedingPermission(this.mapBoxMap)
        }
    }

    private fun checkNeedingPermission(mapboxMap: MapboxMap) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            locationListener = LocationUtil(
                this,
                lifecycle,
                mapboxMap
            )
        } else {
            permissionManager = PermissionsManager(this)
            permissionManager.requestLocationPermissions(this)
        }
    }
}