package com.sharmaumang001.srpbrowser.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.sharmaumang001.srpbrowser.R
import com.sharmaumang001.srpbrowser.fragments.AboutDevelopers
import com.sharmaumang001.srpbrowser.fragments.AboutSRP
import com.sharmaumang001.srpbrowser.fragments.InfoFragment

@SuppressLint("SetTextI18n")
class Info : AppCompatActivity() {
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var frame: FrameLayout
    private lateinit var aboutSRP: Button
    private lateinit var aboutDevelopers: Button
    private lateinit var backToMain: ImageButton
    private lateinit var cardView: CardView
    private lateinit var infoName: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        frame = findViewById(R.id.frame)
        backToMain = findViewById(R.id.backToMain)
        aboutDevelopers = findViewById(R.id.aboutDevelopers)
        aboutSRP = findViewById(R.id.aboutSRP)
        cardView = findViewById(R.id.cardView)
        infoName = findViewById(R.id.infoName)

        cardView.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frame,
                InfoFragment()
            )
            .commit()

        aboutSRP.setOnClickListener {
            cardView.visibility = View.GONE
            infoName.text = "About SRP"
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.frame,
                    AboutSRP()
                )
                .commit()
        }

        aboutDevelopers.setOnClickListener {
            cardView.visibility = View.GONE
            infoName.text = "About Developers"
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.frame,
                    AboutDevelopers()
                )
                .commit()
        }

        backToMain.setOnClickListener {
            when (supportFragmentManager.findFragmentById(R.id.frame)) {
                !is InfoFragment -> {
                    cardView.visibility = View.VISIBLE
                    infoName.text = "About App"
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            InfoFragment()
                        )
                        .commit()
                }
                else -> {
                    startActivity(Intent(this@Info, MainActivity::class.java))
                    finish()
                }
            }
        }
    }

    override fun onBackPressed() {
         when (supportFragmentManager.findFragmentById(R.id.frame)) {
            !is InfoFragment -> {
                cardView.visibility = View.VISIBLE
                infoName.text = "About App"
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.frame,
                        InfoFragment()
                    )
                    .commit()
            }
            else -> {
                startActivity(Intent(this@Info, MainActivity::class.java))
                finish()
            }
        }
    }
}