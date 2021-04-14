package com.app.eventfinder

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.app.eventfinder.databinding.ActivityMainBinding
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.Style

class MainActivity : AppCompatActivity() {

    private lateinit var binder: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token))
        binder = ActivityMainBinding.inflate(layoutInflater)
        with(binder.mapView) {
            this.onCreate(savedInstanceState)
            this.getMapAsync {
                it.setStyle(Style.MAPBOX_STREETS)
            }
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

}