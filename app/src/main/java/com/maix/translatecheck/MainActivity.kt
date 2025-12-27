package com.maix.translatecheck

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

class MainActivity : AppCompatActivity() {

  fun log(msg: String) {
    Log.d("xMx", msg)
  }
  fun Toast_makeText(msg: String) {
    Toast.makeText(this, "Model ready", Toast.LENGTH_LONG).show()
  }

  private val optionsEnFr = TranslatorOptions.Builder()
    .setSourceLanguage(TranslateLanguage.ENGLISH)
    .setTargetLanguage(TranslateLanguage.FRENCH)
    .build()

  private val optionsEnUk = TranslatorOptions.Builder()
    .setSourceLanguage(TranslateLanguage.ENGLISH)
    .setTargetLanguage(TranslateLanguage.UKRAINIAN)
    .build()
  private val englishFrenchTranslator = Translation.getClient(optionsEnFr)
  private val englishUkraineTranslator = Translation.getClient(optionsEnUk)

  fun downloadModel(translator: Translator) {
    val conditions = DownloadConditions.Builder().requireWifi().build()
    translator.downloadModelIfNeeded(conditions)
      .addOnSuccessListener {
        // Model downloaded successfully. You can now start translating.
        log("Model downloaded successfully")
        Toast_makeText("Model ready")
      }
      .addOnFailureListener { exception ->
        // Model couldn’t be downloaded or other internal error.
        log("Model download failed: $exception")
        Toast_makeText("Model download failed")
      }
  }

  private fun copyTextToClipboard(context: Context, text: String) {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("label", text) // "label" is a user-defined description.
    clipboardManager.setPrimaryClip(clipData)
  }

  @SuppressLint("SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    log("onCreate starts /PCR, run 11.")

    val extras = intent.extras
    if (extras != null) {
      val receivedData = extras.getInt("EXTRA_MESSAGE")
      // Display the data
      log("EXTRA_MESSAGE: [$receivedData]")
    }
    enableEdgeToEdge()
    setContentView(R.layout.activity_main)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    // Edit Texts ...
    val editText1 = findViewById<EditText>(R.id.editText1)
    val editText2 = findViewById<EditText>(R.id.editText2)

    // Buttons ...
    val buttonExit = findViewById<Button>(R.id.buttonExit)
    buttonExit.setOnClickListener {
      log("TranslateCheck exit.")
//      finishAffinity();
      finish();
    }
    val buttonTranslate = findViewById<Button>(R.id.buttonTranslate)
    buttonTranslate.setOnClickListener {
      log("Translate button pressed.")
      translateText(editText1, editText2)
    }
    buttonTranslate.setOnLongClickListener { view ->
      // Code to execute on long press
      Toast.makeText(this, "Long press detected!", Toast.LENGTH_SHORT).show()
      // Return true to indicate the event is consumed
      true
    }
    val buttonPaste = findViewById<Button>(R.id.buttonPaste)
    buttonPaste.setOnClickListener {
      log("Paste pressed.")
      val item = clipboardManager.primaryClip?.getItemAt(0)
      if (item != null) {
        val pasteText = item.text
        if (pasteText != null) {
          log("Pasting text: '$pasteText'")
          editText1.setText(pasteText)
          Toast.makeText(this, "Pasted", Toast.LENGTH_SHORT).show()
        }
      }
    }
    val buttonCopy = findViewById<Button>(R.id.buttonCopy)
    buttonCopy.setOnClickListener {
      val copyText: String = editText2.text.toString()
      log("Copy text: '$copyText'")
      copyTextToClipboard(this, copyText)
      Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show()
    }

    val buttonSetup = findViewById<Button>(R.id.buttonSetup)
    buttonSetup.setOnClickListener {
      log("Setup pressed")
      val intent = Intent(this, SetupActivity::class.java)
      startActivity(intent)
    }

    editText2.setOnClickListener {
      Toast.makeText(this, "+++ TEXTVIEW 2 +++", Toast.LENGTH_SHORT).show()
    }

    val text = "Only some part of fifty books"
    log("EN  : '$text'")
//    downloadModel(englishFrenchTranslator)
    downloadModel(englishUkraineTranslator)
//    englishFrenchTranslator.translate(text)
    englishUkraineTranslator.translate(text)
      .addOnSuccessListener { translatedText ->
        // Translation successful.
//        log("FR  : '$translatedText'")
        log("UK  : '$translatedText'")
      }
      .addOnFailureListener { exception ->
        log("ERR : $exception")
      }
  }

  override fun onDestroy() {
    log("onDestroy()")
//    englishFrenchTranslator.close()
    englishUkraineTranslator.close()
    super.onDestroy()
  }

  @SuppressLint("SetTextI18n")
  fun translateText(editText1: EditText, editText2: EditText) {
    log("Test begins...")
    val textExample = "Just some part of the three books."
    val edit = editText1.text.toString()
    val englishText = edit.ifEmpty { textExample }
    log("EN  : '$englishText'")
//    downloadTranslatorIfNeeded() {
      log("download...")
//      englishFrenchTranslator.translate(englishText)
      englishUkraineTranslator.translate(englishText)
        .addOnSuccessListener { translatedText ->
          // Translation successful.
//          log("FR  : '$translatedText'")
          log("UK  : '$translatedText'")
//          editText2.setText("Translate:\'$translatedText'")
          editText2.setText(translatedText)
        }
        .addOnFailureListener { exception ->
          log("ERR : $exception")
          // Error.
          // ...
        }
        .addOnCanceledListener {
          log("addOnCanceledListener")
        }
        .addOnCompleteListener {
          log("addOnCompleteListener")
        }
//    }
    log("Test done.")
  }
}