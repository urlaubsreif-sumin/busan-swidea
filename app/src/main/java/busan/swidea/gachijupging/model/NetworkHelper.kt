package busan.swidea.gachijupging.model

import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.jvm.Throws

abstract class NetworkHelper {

    companion object{
        lateinit var requestQueue: RequestQueue
    }

    fun sendRequest() {

        if(requestQueue != null) {
                val request = makeRequest()

                request.setShouldCache(false)
                requestQueue.add(request)
                Log.d("TEST", "sendRequest")
            }

        }

    abstract fun makeRequest(): StringRequest

    abstract fun processResponse(response: String)






}