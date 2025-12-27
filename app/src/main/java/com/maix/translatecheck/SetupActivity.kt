package com.maix.translatecheck

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
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

    val spinnerTo: Spinner = findViewById(R.id.spinnerTo)
    val items: Array<String> = arrayOf("UKRAINIAN", "ENGLISH", "FRENCH")
    val adapter = ArrayAdapter<String?>(this, android.R.layout.simple_spinner_dropdown_item, items)
    ArrayAdapter.createFromResource(
      this,
      R.array.planets_array,
      android.R.layout.simple_spinner_item
    ).also { adapter ->
      // Specify the layout to use when the list of choices appears.
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
      // Apply the adapter to the spinner.
      spinnerTo.adapter = adapter
      spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
          log("Spinner pos  : [$pos]")
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
          TODO("Not yet implemented")
        }
      }
//      spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//        override fun onNothingSelected(parent: AdapterView<*>?) {
//          log("Not selected")
//        }
//
//        override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
//          // An item is selected. You can retrieve the selected item using
//          // parent.getItemAtPosition(pos).
//          val pos_: Int = parent?.getItemAtPosition(pos) as Int
//          val value = adapter.getItem(pos_) ?: ""
//          log("Spinneer pos  : [$pos_]")
//          log("Spinneer value: '$value'")
//        }
//      }
    }

  }

  override fun onDestroy() {
    log("Setup onDestroy()")
    super.onDestroy()
  }

}