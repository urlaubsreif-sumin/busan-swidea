package busan.swidea.gachijupging.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import busan.swidea.gachijupging.R
import busan.swidea.gachijupging.viewmodel.MapViewModel
import java.lang.IllegalStateException

class AddTrashcanDialogFragment: DialogFragment() {
    private val mapViewModel : MapViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("현재 위치에 쓰레기통을 추가하겠습니까?")
                .setPositiveButton("추가", DialogInterface.OnClickListener {
                        _, _ -> val action = AddTrashcanDialogFragmentDirections.actionAddTrashcanDialogFragmentToRunFragment()

                    val map = mapViewModel.map
                    map.addTrashcanOnMap()

                    requireActivity().findNavController(R.id.nav_host_fragment).navigate(action)
                })
                .setNegativeButton("취소", DialogInterface.OnClickListener {
                        _, _ -> val action = AddTrashcanDialogFragmentDirections.actionAddTrashcanDialogFragmentToRunFragment()

                    requireActivity().findNavController(R.id.nav_host_fragment).navigate(action)
                })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}