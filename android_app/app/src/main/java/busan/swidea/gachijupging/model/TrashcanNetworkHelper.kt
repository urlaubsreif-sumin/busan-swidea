package busan.swidea.gachijupging.model

import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object TrashcanNetworkHelper: NetworkHelper() {

    private const val url = "http://180.226.42.10:8000/trashcan"
    private val trashcanList = mutableListOf<LatLng>()

    fun getTrashcans(): List<LatLng> {
        sendGetRequest()
        return trashcanList
    }

    fun addTrashcan(locationLatLng: LatLng) {
        sendPostRequest(locationLatLng)
    }

    override fun makeGetRequest(): StringRequest {

        val request = object : StringRequest(Request.Method.GET, url,
            Response.Listener { response ->
                processResponse(response)

            }, Response.ErrorListener { error ->
                Log.d("ERROR", "${error.networkResponse.statusCode}")
            }) {
            // request 시 key, value 보낼 때
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                return params
            }

        }
        return request
    }

    override fun makePostRequest(latitude: Double, longitude: Double): StringRequest {
        val request = object : StringRequest(Request.Method.POST, url,
            Response.Listener { response ->
                Log.d("TEST", response)

            }, Response.ErrorListener { error ->
                Log.d("ERROR", "${error.networkResponse.statusCode}")
            }) {
            // request 시 key, value 보낼 때
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["longitude"] = longitude.toString()
                params["latitude"] = latitude.toString()
                return params
            }

        }
        return request
    }

    override fun processResponse(response: String) {
        val gson = Gson()
        val itemType = object: TypeToken<List<LocationGson>>() {}.type
        val locations = gson.fromJson<List<LocationGson>>(response, itemType)

        trashcanList.clear()

        for(location in locations) {
            val latitude = location.latitude
            val longitude = location.longitude
            val latLngEntity = LatLng(latitude, longitude)

            trashcanList.add(latLngEntity)
        }
    }



}