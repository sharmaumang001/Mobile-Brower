package com.example.shmbrowser.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shmbrowser.R

class MyDownloadActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_download)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@MyDownloadActivity, MainActivity::class.java))
    }
}