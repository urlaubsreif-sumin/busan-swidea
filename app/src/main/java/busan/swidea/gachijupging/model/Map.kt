package busan.swidea.gachijupging.model

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task

object Map : LocationListener {
    val mapReadyCallback = MapReadyCallback
    private lateinit var locationManager: LocationManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val CAMERA_ZOOM_LEVEL = 16f
    private val map by lazy {
        mapReadyCallback.googleMap
    }



    fun setLocationManager(manager: LocationManager) {
        locationManager = manager
    }

    fun setFusedLocationClient(client: FusedLocationProviderClient) {
        fusedLocationClient = client
    }


    private fun moveCameraToPosition(position: LatLng) {
        if(map != null) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                position, CAMERA_ZOOM_LEVEL
            ))
        }
    }

    private fun addMarkerOnMap(locationLatLng: LatLng, title: String?, snippet: String?) {
        if(map!= null) {
            val markerOpt = MarkerOptions().apply{
                position(locationLatLng)
                title(title)
                snippet(snippet)
            }
            map.addMarker(markerOpt)
        }
    }

    override fun onLocationChanged(location: Location) {
        val locationLatLng = LatLng(
            location.latitude,
            location.longitude
        )

    }


    object MapReadyCallback: OnMapReadyCallback {
        lateinit var googleMap: GoogleMap

        override fun onMapReady(map: GoogleMap) {
            googleMap = map
            setGoogleMapUI()
        }

        @SuppressLint("MissingPermission")
        fun setGoogleMapUI() {
            if(googleMap != null) {
                googleMap.isMyLocationEnabled = true
                googleMap.uiSettings.isZoomGesturesEnabled = true
                googleMap.uiSettings.isZoomControlsEnabled = true

                val seoul = LatLng(37.56, 126.97)
                addMarkerOnMap(seoul, "seoul", "capital city of SouthKorea")
                moveCameraToPosition(seoul)

                //moveCameraToPosition(LocationLatLngEntity(0f, 0f))
                //startLocationService()
            }
        }
    }



}