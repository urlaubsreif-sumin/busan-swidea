package busan.swidea.gachijupging.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import busan.swidea.gachijupging.R
import busan.swidea.gachijupging.databinding.FragmentRunBinding
import busan.swidea.gachijupging.model.Map
import busan.swidea.gachijupging.viewmodel.TimerViewModel


class RunFragment : Fragment() {

    private lateinit var binding: FragmentRunBinding
    private lateinit var map: Map

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        setMapReady()
    }

    private fun setPauseButton() {
        binding.pauseButton.setOnClickListener {
            TimerViewModel.timerPause()
        }

        binding.pauseButton.setOnLongClickListener {
            TimerViewModel.timerStop()
            val action = RunFragmentDirections.actionRunFragmentToMainFragment()
            requireActivity().findNavController(R.id.nav_host_fragment).navigate(action)
            true
        }
    }

    private fun setMapReady() {
        map = Map
        binding.mapView.getMapAsync(map.mapReadyCallback)
    }

    companion object {


    }
}