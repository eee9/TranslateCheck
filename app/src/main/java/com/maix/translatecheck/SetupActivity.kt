package com.maix.translatecheck

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.maix.translatecheck.databinding.ActivitySetupBinding

class SetupActivity : AppCompatActivity() {

  private lateinit var appBarConfiguration: AppBarConfiguration
  private lateinit var binding: ActivitySetupBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivitySetupBinding.inflate(layoutInflater)
    setContentView(binding.root)


    val navController = findNavController(R.id.nav_host_fragment_content_setup)
    appBarConfiguration = AppBarConfiguration(navController.graph)
    setupActionBarWithNavController(navController, appBarConfiguration)

  }

  override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment_content_setup)
    return navController.navigateUp(appBarConfiguration)
        || super.onSupportNavigateUp()
  }
}