package busan.swidea.gachijupging.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.ui.NavigationUI
import busan.swidea.gachijupging.databinding.ActivityMainBinding
import busan.swidea.gachijupging.model.NetworkHelper
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply{
            lifecycleOwner = this@MainActivity
        }
        setContentView(binding.root)
        setNetwork()
    }

    private fun setNetwork() {
        NetworkHelper.requestQueue = Volley.newRequestQueue(applicationContext)
    }

}