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

//  data class Item(val iconResId: Int, val itemName: String)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    log("onCreate Setup")
    enableEdgeToEdge()
    setContentView(R.layout.activity_setup)

    var OPTIONPOS = 0
    var OPTIONTEXT = ""

    val extras = intent.extras
    if (extras != null) {
      OPTIONPOS = extras.getInt("EXTRA_POS")
      log("EXTRA_POS: [$OPTIONPOS]")
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
      intent.putExtra("EXTRA_MESSAGE", OPTIONTEXT)
      intent.putExtra("EXTRA_POS", OPTIONPOS)
      startActivity(intent)
      finish()
    }

    val spinnerTo: Spinner = findViewById(R.id.spinnerTo)
    ArrayAdapter.createFromResource(this, R.array.languages,
      android.R.layout.simple_spinner_item
    ).also { adapter ->
      // Specify the layout to use when the list of choices appears.
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
      // Apply the adapter to the spinner.
      spinnerTo.adapter = adapter
      spinnerTo.setSelection(OPTIONPOS)
      spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
          log("Spinner pos  : [$pos]")
          OPTIONTEXT = spinnerTo.getSelectedItem().toString()
          log("Spinner text : '$OPTIONTEXT'")
          OPTIONPOS = pos
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
          TODO("Not yet implemented")
        }
      }
    }
  }

  override fun onDestroy() {
    log("Setup onDestroy()")
    super.onDestroy()
  }

}