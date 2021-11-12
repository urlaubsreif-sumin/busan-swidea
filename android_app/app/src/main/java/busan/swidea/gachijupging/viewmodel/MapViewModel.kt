package busan.swidea.gachijupging.viewmodel

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import busan.swidea.gachijupging.model.GoogleMap

class MapViewModel: ViewModel() {
    lateinit var map: GoogleMap

    fun setMapWithLifecycle(lifecycle: Lifecycle){
        map = GoogleMap(lifecycle)
        Log.d("TEST", "map set !!!")
    }

}