package com.hesham0_0.movietrainingapplication

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
import androidx.activity.compose.setContent
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.hesham0_0.movietrainingapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        window.setFlags(FLAG_LAYOUT_NO_LIMITS, FLAG_LAYOUT_NO_LIMITS)
//        statusIconColor(this)
    }
//   private fun statusIconColor(activity: Activity){
//       if (Build.VERSION.SDK_INT>=23){
//           val view=activity.window.decorView
//           if (view.systemUiVisibility!= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
//               view.systemUiVisibility =View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//           else
//               view.systemUiVisibility=0
//       }
//   }
}