package com.sharmaumang001.srpbrowser.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.sharmaumang001.srpbrowser.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val startAct = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(startAct)
            finish()
        }, 900)
    }
}