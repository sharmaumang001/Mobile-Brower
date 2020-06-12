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

    private lateinit var imageButton5: ImageButton
    private lateinit var imageButton6: ImageButton
    private lateinit var imageButton7: ImageButton
    private lateinit var imageButton8: ImageButton
    private lateinit var recyclerDownloads: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var scrollView: ScrollView
    private lateinit var ll: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_download)

        imageButton5 = findViewById(R.id.imageButton5)
        imageButton6 = findViewById(R.id.imageButton6)
        imageButton7 = findViewById(R.id.imageButton7)
        imageButton8 = findViewById(R.id.imageButton8)
        recyclerDownloads = findViewById(R.id.recyclerDownloads)
        layoutManager = LinearLayoutManager(this@MyDownloadActivity)
        scrollView = findViewById(R.id.scrollView)
        ll = findViewById(R.id.ll)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@MyDownloadActivity, MainActivity::class.java))
    }
}