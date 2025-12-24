package com.maix.translatecheck

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SetupActivity : AppCompatActivity() {

  fun log(msg: String) {
    Log.d("xMx Setup", msg)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    log("onCreate Setup")
    enableEdgeToEdge()
    setContentView(R.layout.activity_setup)

    // Buttons ...
    val buttonCancel = findViewById<Button>(R.id.buttonCancel)
    buttonCancel.setOnClickListener {
      log("Setup exit.")
      finish();
    }

  }

  override fun onDestroy() {
    log("Setup onDestroy()")
    super.onDestroy()
  }
}