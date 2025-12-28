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
  fun Toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
  }

  var LANG_FROM_POS = 0
  var LANG_FROM_TEXT = "ENGLISH"
  var LANG_TO_POS = 2
  var LANG_TO_TEXT = "UKRAINIAN"

  private var options = TranslatorOptions.Builder()
    .setSourceLanguage(TranslateLanguage.ENGLISH)
    .setTargetLanguage(TranslateLanguage.UKRAINIAN)
    .build()

  private var commonTranslator = Translation.getClient(options)


  fun downloadModel(translator: Translator, button: Button) {
//    englishUkraineTranslator
    val conditions = DownloadConditions.Builder().requireWifi().build()
    translator.downloadModelIfNeeded(conditions)
      .addOnSuccessListener {
        // Model downloaded successfully. You can now start translating.
        log("Model downloaded successfully")
        Toast("Model ready")
        button.isEnabled = true
      }
      .addOnFailureListener { exception ->
        // Model couldn’t be downloaded or other internal error.
        log("Model download failed: $exception")
        Toast("Model download failed")
        button.isEnabled = false
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
    log("onCreate starts /PCS, v.0.1.22")
    enableEdgeToEdge()
    val extras = intent.extras
    if (extras != null) {
      LANG_FROM_TEXT = extras.getString("SETUP_LANG_FROM_TEXT") ?: ""
      LANG_FROM_POS = extras.getInt("SETUP_LANG_FROM_POS")
      LANG_TO_TEXT = extras.getString("SETUP_LANG_TO_TEXT") ?: ""
      LANG_TO_POS = extras.getInt("SETUP_LANG_TO_POS")
      log("SETUP_LANG_FROM_TEXT: '$LANG_FROM_TEXT'")
      log("SETUP_LANG_FROM_POS : [$LANG_FROM_POS]")
      log("SETUP_LANG_TO_TEXT  : '$LANG_TO_TEXT'")
      log("SETUP_LANG_TO_POS   : [$LANG_TO_POS]")
    }

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
    buttonTranslate.isEnabled = false
    buttonTranslate.text = doButtonText(LANG_FROM_TEXT, LANG_TO_TEXT)
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
      intent.putExtra("MAIN_LANG_FROM_POS", LANG_FROM_POS)
      intent.putExtra("MAIN_LANG_TO_POS", LANG_TO_POS)
      startActivity(intent)
    }

    editText2.setOnClickListener {
      Toast.makeText(this, "+++ TEXTVIEW 2 +++", Toast.LENGTH_SHORT).show()
    }

    setupTranslator(buttonTranslate, LANG_FROM_TEXT, LANG_TO_TEXT)
  }

  fun doButtonText(lang1: String, lang2: String): String {
    return lang1.take(2).uppercase() + " => " + lang2.take(2).uppercase()
  }

  fun getLanguage(lang: String): String {
    var res = ""
    when (lang.uppercase()) {
      "ENGLISH" -> res = TranslateLanguage.ENGLISH
      "FRENCH" -> res = TranslateLanguage.FRENCH
      "UKRAINIAN" -> res = TranslateLanguage.UKRAINIAN
      "RUSSIAN" -> res = TranslateLanguage.RUSSIAN
      "ITALIAN" -> res = TranslateLanguage.ITALIAN
      "SPANISH" -> res = TranslateLanguage.SPANISH
      "GERMAN" -> res = TranslateLanguage.GERMAN
    }
    return res
  }

  fun setupTranslator(button: Button, lang1: String, lang2: String) {
    button.isEnabled = false
    button.text = " MODEL Loading..."
    commonTranslator.close()
    val langFrom: String = getLanguage(lang1)
    val langTo: String = getLanguage(lang2)
    if (langTo.isEmpty() || langFrom.isEmpty()) return
    log("Translate: '$lang1' => '$lang2'")
    options = TranslatorOptions.Builder()
      .setSourceLanguage(langFrom)
      .setTargetLanguage(langTo)
      .build()
    commonTranslator = Translation.getClient(options)

    val text = "Only some part of fifty books"
    log("LANG1 : '$text'")
    downloadModel(commonTranslator, button)
    button.text = doButtonText(lang1, lang2)
    commonTranslator.translate(text)
      .addOnSuccessListener { translatedText ->
        // Translation successful.
        log("LANG2 : '$translatedText'")
      }
      .addOnFailureListener { exception ->
        log("ERR : $exception")
      }
  }

  override fun onDestroy() {
    log("onDestroy()")
    commonTranslator.close()
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
      commonTranslator.translate(englishText)
        .addOnSuccessListener { translatedText ->
          log("LANG2 : '$translatedText'")
          editText2.setText(translatedText)
        }
        .addOnFailureListener { exception ->
          log("ERR : $exception")
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