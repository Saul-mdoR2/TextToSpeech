package com.example.texttospeech

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var etText: EditText
    private lateinit var btn: Button
    private lateinit var tts: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TextToSpeech(this, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts.setLanguage(Locale.US)
                // val result = tts.setLanguage(Locale("spa","MX"))

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "The Language specified is not supported!")
                } else {
                    btn.isEnabled = true
                }
            } else {
                Log.e("TTS", "Initilization Failed!")
            }
        })

        etText = findViewById(R.id.etText)
        btn = findViewById(R.id.btnLeer)
        btn.setOnClickListener {
            speakOut()
        }
    }

    private fun speakOut() {

        val text = etText.text.toString()
        // REPRODUCIR AUDIO
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")

        // ALMACENAR AUDIO EN FORMATO MP3
        val root = getExternalFilesDir(null)
        val dir = File(root?.absolutePath.toString() + "/download")
        dir.mkdirs()
        val file = File(dir, "${"aud_" + Random().nextInt()}.mp3")
        tts.synthesizeToFile(text as CharSequence, null, file, "tts")
    }

    public override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }

}