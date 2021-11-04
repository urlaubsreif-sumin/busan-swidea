package busan.swidea.gachijupging.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import busan.swidea.gachijupging.R
import busan.swidea.gachijupging.databinding.FragmentRecordBinding


/**
 * A simple [Fragment] subclass.
 * Use the [RecordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecordFragment : Fragment() {
    private lateinit var binding: FragmentRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentRecordBinding>(inflater,
            R.layout.fragment_record, container, false)

        binding.apply{
            lifecycleOwner = this@RecordFragment
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {

    }
}