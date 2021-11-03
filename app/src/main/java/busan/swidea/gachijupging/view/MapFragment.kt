package busan.swidea.gachijupging.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import busan.swidea.gachijupging.R
import busan.swidea.gachijupging.databinding.FragmentMapBinding
import busan.swidea.gachijupging.model.Map

/**
 * A simple [Fragment] subclass.
 * Use the [RunningFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private lateinit var map: Map

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentMapBinding>(inflater,
            R.layout.fragment_map, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        setMapReady()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onPause() {
        binding.mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        binding.mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }



    private fun setMapReady() {
        if(hasPermission()) {
            map = Map()
            binding.mapView.getMapAsync(map.mapReadyCallback)
        }
    }


    private fun hasPermission(): Boolean {
        if (checkPermission()) {
            return true
        } else {
            val result = requestPermission()
            return result
        }
    }

    private fun checkPermission(): Boolean {
        if(checkCoarseLocationPermission() && checkFineLocationPermission()) {
            return true
        }
        return false
    }

    private fun checkCoarseLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED)
            return true
        return false
    }

    private fun checkFineLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED)
                return true
        return false
    }

    private fun requestPermission(): Boolean {
        var isPermit = false
        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                isGranted: Boolean ->
            isPermit = isGranted
        }
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        return isPermit
    }




    companion object {

    }

}