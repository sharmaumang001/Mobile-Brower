package com.sharmaumang001.srpbrowser.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sharmaumang001.srpbrowser.Adapter.BrowserHistoryRecyclerAdapter
import com.sharmaumang001.srpbrowser.Database.FunctionsHistory
import com.sharmaumang001.srpbrowser.Database.HistoryEntity
import com.sharmaumang001.srpbrowser.R

class BrowserHistoryActivity : AppCompatActivity() {
    lateinit var scrollView: ScrollView
    lateinit var recyclerHistory: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: BrowserHistoryRecyclerAdapter
    lateinit var back: ImageButton
    lateinit var texthistory: TextView
    lateinit var clearData: Button
    val historyList= arrayListOf<HistoryEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser_history)
        scrollView=findViewById(R.id.scrollView)
        recyclerHistory = findViewById(R.id.recyclerHistory)
        back = findViewById(R.id.back)
        clearData = findViewById(R.id.clearData)
        texthistory = findViewById(R.id.texthistory)
        layoutManager = LinearLayoutManager(this@BrowserHistoryActivity)

        val list = FunctionsHistory.GetAll(this@BrowserHistoryActivity).execute().get()
        val count = FunctionsHistory.Count(this@BrowserHistoryActivity).execute().get()
        if(list.isEmpty()){
            texthistory.visibility = View.VISIBLE
            scrollView.visibility = View.GONE
        }
        else{
            texthistory.visibility = View.GONE
            scrollView.visibility = View.VISIBLE
            historyList.addAll(list)
            recyclerAdapter = BrowserHistoryRecyclerAdapter(this@BrowserHistoryActivity, historyList)
            recyclerHistory.adapter = recyclerAdapter
            recyclerHistory.layoutManager = layoutManager
        }

        clearData.setOnClickListener {
            if(list.isNotEmpty()) {
                FunctionsHistory.ClearData(this@BrowserHistoryActivity).execute()
                recyclerAdapter.notifyDataSetChanged()
                texthistory.visibility = View.VISIBLE
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