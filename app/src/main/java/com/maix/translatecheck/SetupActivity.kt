package com.maix.translatecheck

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SetupActivity : AppCompatActivity() {

  fun log(msg: String) {
    Log.d("xMx Setup", msg)
  }

  data class Item(val iconResId: Int, val itemName: String)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    log("onCreate Setup")
    enableEdgeToEdge()
    setContentView(R.layout.activity_setup)

    val items4 = listOf("A", "B", "C", "D", "E") // Replace with your data list

    val customSpinnerAdapter = ArrayAdapter(
      this,
      android.R.layout.simple_spinner_dropdown_item,
      items4
    )

    val searchableSpinner = findViewById<CustomSpinner>(R.id.searchable_spinner)
    searchableSpinner.setAdapter(customSpinnerAdapter)

    searchableSpinner.onClearClick {
      // Handle clear button click event
      searchableSpinner.clearSelectedText()
      // Additional actions if needed
    }

    var OPTION: Int = 0
//    val LANGS = MainActivity().LANGS

    // Buttons ...
    val buttonCancel = findViewById<Button>(R.id.buttonCancel)
    buttonCancel.setOnClickListener {
      log("Setup exit.")
      finish();
    }

    val buttonOK = findViewById<Button>(R.id.buttonOK)
    buttonOK.setOnClickListener {
      log("OK pressed.")
      val intent: Intent = Intent(this, MainActivity::class.java)
      // Put the data as an extra in the Intent
      intent.putExtra("EXTRA_MESSAGE", OPTION) // "EXTRA_MESSAGE" is a unique key

      // Start Activity B
      startActivity(intent)
    }

    // Prepare data for the spinner
//    val items3 = listOf(
//      Item(1, "Pizza"),
//      Item(2, "Burger"),
//      Item(3, "Salad"),
//      Item(4, "Ice cream"),
//      Item(5, "Cold Drink"),
//      Item(6, "Pasta")
//    )

    log("Before spinnerTo")
    val spinnerTo: Spinner = findViewById(R.id.spinnerTo)
    val items: Array<String> = arrayOf("UKRAINIAN", "ENGLISH", "FRENCH")
    val items2 = R.array.planets_array2
    val t: Int = items2

    // Create and set the custom adapter for the spinner
//    val adapter = ItemAdapter(this, items3)
//    spinnerTo.adapter = adapter
//    // Handle item selection
//    spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        val selectedItem = items[position]
//        // Show a toast message with the selected item's name
//        val name = selectedItem
//        val msg = "Selected: [$selectedItem]"
//        log(msg)
//        Toast.makeText(MainActivity(), msg, Toast.LENGTH_SHORT).show()
//      }
//
//      override fun onNothingSelected(parent: AdapterView<*>?) {
//        // Handle case when nothing is selected if needed
//      }
//    }

    val adapter = ArrayAdapter<String?>(this, android.R.layout.simple_spinner_dropdown_item, items)
    log("Before create")
//    ArrayAdapter.createFromResource(this, R.array.planets_array,
//      android.R.layout.simple_spinner_item
    searchableSpinner.setAdapter(adapter)
//    ArrayAdapter.createFromResource(this, items2,
//      android.R.layout.simple_spinner_item
//    ).also { adapter ->
//      // Specify the layout to use when the list of choices appears.
//      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//      // Apply the adapter to the spinner.
//      spinnerTo.adapter = adapter
//      spinnerTo.setSelection(1)
//      spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//        override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
//          log("Spinner pos  : [$pos]")
//          OPTION = pos
//        }
//        override fun onNothingSelected(parent: AdapterView<*>?) {
//          TODO("Not yet implemented")
//        }
//      }
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
//    }

  }

  override fun onDestroy() {
    log("Setup onDestroy()")
    super.onDestroy()
  }

//  // Custom adapter for the spinner
//  class ItemAdapter(private val context: Context, private val items: List<SetupActivity.Item>) : BaseAdapter() {
//
//    override fun getCount(): Int = items.size
//
//    override fun getItem(position: Int): Any = items[position]
//
//    override fun getItemId(position: Int): Long = position.toLong()
//
//    // Create and return the view for each item in the spinner
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//
//      val inflater = LayoutInflater.from(context)
////      val view = inflater.inflate(R.layout.simple_spinner_dropdown_item, parent, false)
//
////      val item = getItem(position) as SetupActivity.Item
//
//
//      return view
//    }
//  }

}