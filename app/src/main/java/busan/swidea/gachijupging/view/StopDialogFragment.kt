package busan.swidea.gachijupging.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import busan.swidea.gachijupging.R
import busan.swidea.gachijupging.viewmodel.TimerViewModel
import java.lang.IllegalStateException

class StopDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("종료하시겠습니까?")
                .setPositiveButton("종료", DialogInterface.OnClickListener {
                        _, _ -> val action = StopDialogFragmentDirections.actionStopDialogFragmentToMainFragment()
                    TimerViewModel.timerStop()
                    requireActivity().findNavController(R.id.nav_host_fragment).navigate(action)
                })
                .setNegativeButton("취소", DialogInterface.OnClickListener {
                        _, _ -> val action = StopDialogFragmentDirections.actionStopDialogFragmentToRunFragment()
                    TimerViewModel.timerStart()
                    requireActivity().findNavController(R.id.nav_host_fragment).navigate(action)
                })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}