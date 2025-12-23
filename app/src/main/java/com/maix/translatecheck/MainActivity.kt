package com.maix.translatecheck

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
  @SuppressLint("SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_main)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    // Buttons ...
    val buttonExit = findViewById<Button>(R.id.buttonExit)
    val buttonTranslate = findViewById<Button>(R.id.buttonTranslate)
    buttonExit.setOnClickListener {
      finishAffinity();
    }
    // Edit Texts ...
    val editText1 = findViewById<EditText>(R.id.editText1)
    val editText2 = findViewById<EditText>(R.id.editText2)
    editText1.setText("New text 1\n".repeat(55))
    editText2.setText("Some text 2 new\n".repeat(45))
    editText1.setTextIsSelectable(true);
    editText1.editableText
    editText1.setText(editText1.getText(), TextView.BufferType.EDITABLE);
    editText1.setOnClickListener {
      Toast.makeText(this, "TextView 1 clicked!", Toast.LENGTH_SHORT).show()
    }
    editText2.setOnClickListener {
      Toast.makeText(this, "+++ TEXTVIEW 2 +++", Toast.LENGTH_SHORT).show()
    }
  }
  
  fun translateText() {
    finishAffinity();
  }
}