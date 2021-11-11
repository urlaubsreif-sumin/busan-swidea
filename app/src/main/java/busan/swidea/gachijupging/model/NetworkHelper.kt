package busan.swidea.gachijupging.model

import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.google.android.gms.maps.model.LatLng

abstract class NetworkHelper {

    companion object{
        lateinit var requestQueue: RequestQueue
    }

    fun sendGetRequest() {

        if(requestQueue != null) {
            val request = makeGetRequest()

            request.setShouldCache(false)
            requestQueue.add(request)
            Log.d("TEST", "sendGetRequest")
        }
    }

    fun sendPostRequest(latlng: LatLng) {
        if(requestQueue != null) {
            val request = makePostRequest(latlng.latitude, latlng.longitude)

            request.setShouldCache(false)
            requestQueue.add(request)
            Log.d("TEST", "sendPostRequest")
        }
    }

    abstract fun makeGetRequest(): StringRequest

    abstract fun makePostRequest(latitude: Double, longitude: Double): StringRequest

    abstract fun processResponse(response: String)






}