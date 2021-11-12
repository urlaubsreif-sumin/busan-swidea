package busan.swidea.gachijupging.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import busan.swidea.gachijupging.R
import busan.swidea.gachijupging.databinding.FragmentStartBinding
import busan.swidea.gachijupging.model.GoogleMap
import busan.swidea.gachijupging.viewmodel.TimerViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

/**
 * A simple [Fragment] subclass.
 * Use the [RunningFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentStartBinding>(inflater,
            R.layout.fragment_start, container, false)

        binding.apply {
            lifecycleOwner = this@StartFragment
            timerViewModel = TimerViewModel
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        setMapReady()
        setStartButton()
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
            map = GoogleMap(lifecycle)
            viewLifecycleOwner.lifecycle.addObserver(map)

            map.setLocationManager(getLocationManager())
            map.setFusedLocationClient(getFusedLocationClient())
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

    private fun getLocationManager(): LocationManager {
        return requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private fun getFusedLocationClient(): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    private fun setStartButton() {
        binding.startBtn.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToRunFragment()
            requireActivity().findNavController(R.id.nav_host_fragment).navigate(action)
        }
    }




    companion object {

    }

}