package busan.swidea.gachijupging.model

import android.annotation.SuppressLint
import android.location.LocationManager
import android.os.Looper
import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import busan.swidea.gachijupging.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GoogleMap(var lifecycle: Lifecycle): LifecycleObserver {
    val mapReadyCallback = MapReadyCallback
    private lateinit var locationManager: LocationManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val CAMERA_ZOOM_LEVEL = 16f
    private lateinit var currentLocation: LatLng
    private val map by lazy {
        mapReadyCallback.googleMap
    }
    private lateinit var locationCallback: LocationCallback


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(){
        initLocation()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(){
        requestLocation()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause(){
        removeLocationRequest()
    }


    fun setLocationManager(manager: LocationManager) {
        locationManager = manager
    }

    fun setFusedLocationClient(client: FusedLocationProviderClient) {
        fusedLocationClient = client
    }


    private fun moveCameraToPosition(position: LatLng) {
        if (map != null) {
            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    position, CAMERA_ZOOM_LEVEL
                )
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun initLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location == null) {

                } else {
                    val locationLatLng = LatLng(location.latitude, location.longitude)
                    moveCameraToPosition(locationLatLng)
                }
            }
            .addOnFailureListener {

            }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 1000
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val location = locationResult.lastLocation
                currentLocation = LatLng(location.latitude, location.longitude)
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    private fun removeLocationRequest() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    fun addTrashcanOnMap() {
        if (map != null) {
            val markerOpt = MarkerOptions().apply {
                position(currentLocation)
                title("????????????")
            }
            TrashcanNetworkHelper.addTrashcan(currentLocation)
            refreshMap()
        }
    }

    fun addHotspotOnMap() {
        if(map != null) {
            val markerOpt = MarkerOptions().apply {
                position(currentLocation)
                title("?????????")
            }
            HotspotNetworkHelper.addHotspot(currentLocation)
            refreshMap()
        }
    }

   private fun refreshMap() {
        map.clear()
        setTrashcans()
        setHotspots()
    }


    companion object MapReadyCallback : OnMapReadyCallback {
        lateinit var googleMap: GoogleMap

        override fun onMapReady(map: GoogleMap) {
            googleMap = map
            setGoogleMapUI()

            setTrashcans()
            setHotspots()
            addClickListener()
        }

        @SuppressLint("MissingPermission")
        fun setGoogleMapUI() {
            if (googleMap != null) {
                googleMap.isMyLocationEnabled = true
                googleMap.uiSettings.isZoomGesturesEnabled = true
                googleMap.uiSettings.isZoomControlsEnabled = true
            }
        }

        private fun addClickListener() {
            if(googleMap != null) {
                googleMap.setOnMapClickListener { location ->
                    addMarkerOnMap(location, "", "")
                }
            }
        }

        private fun setTrashcans() {
            val trashcans = TrashcanNetworkHelper.getTrashcans()
            for(t in trashcans) {
                addMarkerOnMap(t, "????????????", "")
            }
        }

        private fun setHotspots() {
            val hotspots = HotspotNetworkHelper.getHotspots()
            for(h in hotspots){
                addMarkerOnMap(h, "?????????", "")
            }
        }

        private fun addMarkerOnMap(locationLatLng: LatLng, title: String?, snippet: String?) {
            if (googleMap != null) {
                val markerOpt = MarkerOptions().apply {
                    position(locationLatLng)
                    title(title)
                    snippet(snippet)
                }
                googleMap.addMarker(markerOpt)
            }
        }


    }


}