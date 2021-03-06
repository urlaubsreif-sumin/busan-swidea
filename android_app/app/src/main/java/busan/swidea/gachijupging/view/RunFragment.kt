package busan.swidea.gachijupging.view

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import busan.swidea.gachijupging.R
import busan.swidea.gachijupging.databinding.FragmentRunBinding
import busan.swidea.gachijupging.model.GoogleMap
import busan.swidea.gachijupging.model.NetworkHelper
import busan.swidea.gachijupging.viewmodel.MapViewModel
import busan.swidea.gachijupging.viewmodel.TimerViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class RunFragment : Fragment() {

    private lateinit var binding: FragmentRunBinding
    private val mapViewModel : MapViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapViewModel.setMapWithLifecycle(lifecycle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_run, container, false)
        binding.apply {
            lifecycleOwner = this@RunFragment
            timerViewModel = TimerViewModel
            state.timerViewModel = TimerViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
        binding.mapView.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
        TimerViewModel.timerStart()
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


    private fun setUI(){
        setPauseButton()
        setPlayButton()
        setAddButtons()
        setMapReady()
    }

    private fun setPauseButton() {
        binding.pauseButton.setOnClickListener {
            TimerViewModel.timerPause()
            binding.pausedView.apply {
                visibility = View.VISIBLE
            }
            binding.pauseButton.apply {
                visibility = View.GONE
            }
            binding.playButton.apply {
                visibility = View.VISIBLE
            }
        }

        binding.pauseButton.setOnLongClickListener {
            TimerViewModel.timerPause()
            val action = RunFragmentDirections.actionRunFragmentToStopDialogFragment()
            requireActivity().findNavController(R.id.nav_host_fragment).navigate(action)
            true
        }
    }

    private fun setPlayButton() {
        binding.playButton.setOnClickListener {
            TimerViewModel.timerStart()
            binding.playButton.apply {
                visibility = View.GONE
            }
            binding.pauseButton.apply{
                visibility = View.VISIBLE
            }
            binding.pausedView.apply {
                visibility = View.GONE
            }
        }
    }

    private fun setAddButtons() {
        binding.addTrashcanButton.setOnClickListener {
            val action = RunFragmentDirections.actionRunFragmentToAddTrashcanDialogFragment()
            requireActivity().findNavController(R.id.nav_host_fragment).navigate(action)
        }

        binding.addHotspotButton.setOnClickListener {
            val action = RunFragmentDirections.actionRunFragmentToAddHotspotDialogFragment()
            requireActivity().findNavController(R.id.nav_host_fragment).navigate(action)
        }
    }

    private fun setMapReady() {
        val map = mapViewModel.map
        viewLifecycleOwner.lifecycle.addObserver(map)

        map.apply {
            setLocationManager(getLocationManager())
            setFusedLocationClient(getFusedLocationClient())
        }

        binding.mapView.getMapAsync(mapViewModel.map.mapReadyCallback)
    }

    private fun getLocationManager(): LocationManager {
        return requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private fun getFusedLocationClient(): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    



    companion object {


    }
}