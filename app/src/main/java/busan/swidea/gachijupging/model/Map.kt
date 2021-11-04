package busan.swidea.gachijupging.model

import android.annotation.SuppressLint
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback

object Map {
    val mapReadyCallback = MapReadyCallback
    private val map by lazy {
        mapReadyCallback.googleMap
    }

    object MapReadyCallback: OnMapReadyCallback {
        lateinit var googleMap: GoogleMap

        override fun onMapReady(map: GoogleMap) {
            googleMap = map
            enableMyLocation()
        }

        @SuppressLint("MissingPermission")
        fun enableMyLocation() {
            if(googleMap != null) {
                googleMap.isMyLocationEnabled = true
            }
        }
    }

}