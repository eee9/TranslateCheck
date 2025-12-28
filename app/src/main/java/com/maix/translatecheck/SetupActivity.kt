package com.maix.translatecheck

import android.content.Intent
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

    var LANG_FROM_POS = 0
    var LANG_FROM_TEXT = "ENGLISH"
    var LANG_TO_POS = 2
    var LANG_TO_TEXT = "UKRAINIAN"

    val extras = intent.extras
    if (extras != null) {
      LANG_FROM_POS = extras.getInt("MAIN_LANG_FROM_POS")
      LANG_TO_POS = extras.getInt("MAIN_LANG_TO_POS")
      log("MAIN_LANG_FROM_POS: [$LANG_FROM_POS]")
      log("MAIN_LANG_TO_POS  : [$LANG_TO_POS]")
    }

    // Buttons ...
    val buttonCancel = findViewById<Button>(R.id.buttonCancel)
    buttonCancel.setOnClickListener {
      log("Setup exit.")
      finish();
    }

    val buttonOK = findViewById<Button>(R.id.buttonOK)
    buttonOK.setOnClickListener {
      log("OK pressed.")
      val intent = Intent(this, MainActivity::class.java)
      // Put the data as an extra in the Intent
      intent.putExtra("SETUP_LANG_FROM_TEXT", LANG_FROM_TEXT)
      intent.putExtra("SETUP_LANG_FROM_POS", LANG_FROM_POS)
      intent.putExtra("SETUP_LANG_TO_TEXT", LANG_TO_TEXT)
      intent.putExtra("SETUP_LANG_TO_POS", LANG_TO_POS)
//      intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
      startActivity(intent)
      finish()
    }

    val spinnerFrom: Spinner = findViewById(R.id.spinnerFrom)
    adapter(spinnerFrom, LANG_FROM_POS)
    spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        log("SpinnerFrom pos  : [$pos]")
        LANG_FROM_TEXT = spinnerFrom.getSelectedItem().toString()
        log("SpinnerFrom text : '$LANG_FROM_TEXT'")
        LANG_FROM_POS = pos
      }
      override fun onNothingSelected(parent: AdapterView<*>?) { TODO("Not yet implemented") }
    }
    val spinnerTo: Spinner = findViewById(R.id.spinnerTo)
    adapter(spinnerTo, LANG_TO_POS)
    spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        log("SpinnerTo pos  : [$pos]")
        LANG_TO_TEXT = spinnerTo.getSelectedItem().toString()
        log("SpinnerTo text : '$LANG_TO_TEXT'")
        LANG_TO_POS = pos
      }
      override fun onNothingSelected(parent: AdapterView<*>?) { TODO("Not yet implemented") }
    }

  }

  override fun onDestroy() {
    log("Setup onDestroy()")
    super.onDestroy()
  }

  fun adapter(spinner: Spinner, pos: Int) {
    ArrayAdapter.createFromResource(this, R.array.languages,
      android.R.layout.simple_spinner_item
    ).also { adapter ->
      // Specify the layout to use when the list of choices appears.
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
      // Apply the adapter to the spinner.
      spinner.adapter = adapter
      spinner.setSelection(pos)

    }
  }

}