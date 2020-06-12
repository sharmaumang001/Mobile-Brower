package com.example.shmbrowser.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.shmbrowser.R

class MyAudioQueueActivity: AppCompatActivity(){
    lateinit var back: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_audio_queue)
        back = findViewById(R.id.imageButton2)

        back.setOnClickListener {
            startActivity(Intent(this@MyAudioQueueActivity, MainActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@MyAudioQueueActivity, MainActivity::class.java))
        finish()
    }
}