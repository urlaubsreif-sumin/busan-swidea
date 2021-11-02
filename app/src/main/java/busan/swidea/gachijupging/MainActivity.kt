package busan.swidea.gachijupging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.ui.NavigationUI
import busan.swidea.gachijupging.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}