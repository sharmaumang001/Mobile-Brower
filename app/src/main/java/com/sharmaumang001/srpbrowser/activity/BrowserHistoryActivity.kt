package com.sharmaumang001.srpbrowser.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sharmaumang001.srpbrowser.R
import com.sharmaumang001.srpbrowser.adapter.BrowserHistoryRecyclerAdapter
import com.sharmaumang001.srpbrowser.database.FunctionsHistory
import com.sharmaumang001.srpbrowser.database.HistoryEntity

class BrowserHistoryActivity : AppCompatActivity() {
    private lateinit var scrollView: ScrollView
    private lateinit var recyclerHistory: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var recyclerAdapter: BrowserHistoryRecyclerAdapter
    private lateinit var back: ImageButton
    private lateinit var textHistory: TextView
    private lateinit var clearData: Button
    private val historyList = arrayListOf<HistoryEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser_history)
        scrollView = findViewById(R.id.scrollView)
        recyclerHistory = findViewById(R.id.recyclerHistory)
        back = findViewById(R.id.back)
        clearData = findViewById(R.id.clearData)
        textHistory = findViewById(R.id.text_history)
        layoutManager = LinearLayoutManager(this@BrowserHistoryActivity)

        val list = FunctionsHistory.GetAll(this@BrowserHistoryActivity).execute().get()
        val count = FunctionsHistory.Count(this@BrowserHistoryActivity).execute().get()
        if (list.isEmpty()) {
            textHistory.visibility = View.VISIBLE
            scrollView.visibility = View.GONE
        } else {
            textHistory.visibility = View.GONE
            scrollView.visibility = View.VISIBLE
            historyList.addAll(list)
            recyclerAdapter = BrowserHistoryRecyclerAdapter(this@BrowserHistoryActivity, historyList)
            recyclerHistory.adapter = recyclerAdapter
            recyclerHistory.layoutManager = layoutManager
        }

        clearData.setOnClickListener {
            if (list.isNotEmpty()) {
                FunctionsHistory.ClearData(this@BrowserHistoryActivity).execute()
                recyclerAdapter.notifyDataSetChanged()
                textHistory.visibility = View.VISIBLE
                scrollView.visibility = View.GONE
            }
        }

        back.setOnClickListener {
            startActivity(Intent(this@BrowserHistoryActivity, MainActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@BrowserHistoryActivity, MainActivity::class.java))
        finish()
    }
}