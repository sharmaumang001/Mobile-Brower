package com.sharmaumang001.srpbrowser.Activity

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.sharmaumang001.srpbrowser.Adapter.BookmarkRecyclerAdapter
import com.sharmaumang001.srpbrowser.Database.BookmarkDatabase
import com.sharmaumang001.srpbrowser.Database.BookmarkEntity
import com.sharmaumang001.srpbrowser.R

class MyBookmarks: AppCompatActivity() {
    lateinit var search: ImageButton
    lateinit var cancel: ImageButton
    lateinit var back: ImageButton
    lateinit var search_bar: EditText
    lateinit var noBookMark: TextView
    lateinit var star: ImageView
    lateinit var recyclerBookmarks: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: BookmarkRecyclerAdapter
    val searchList = arrayListOf<BookmarkEntity>()
    val bookmarkList = arrayListOf<BookmarkEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mybookmarks)


        search = findViewById(R.id.search_button)
        cancel = findViewById(R.id.cancelButton_ID)
        back = findViewById(R.id.backButton_ID)
        noBookMark = findViewById(R.id.nobookmark_textview)
        star = findViewById(R.id.star_imageview)
        search_bar = findViewById(R.id.search_bar)
        layoutManager = LinearLayoutManager(this@MyBookmarks)
        recyclerBookmarks = findViewById(R.id.recyclerBookmarks)

        noBookMark.visibility = View.GONE
        star.visibility = View.GONE



        val list = GetAllBookmarks(this@MyBookmarks).execute().get()
        if(list.isEmpty()){
            noBookMark.visibility = View.VISIBLE
            star.visibility = View.VISIBLE
            recyclerBookmarks.visibility = View.GONE
        }
        else {
            bookmarkList.addAll(list)
            recyclerAdapter = BookmarkRecyclerAdapter(this@MyBookmarks, bookmarkList)
            recyclerBookmarks.adapter = recyclerAdapter
            recyclerBookmarks.layoutManager = layoutManager
            recyclerBookmarks.visibility = View.VISIBLE
            noBookMark.visibility = View.GONE
            star.visibility = View.GONE
        }

        search.setOnClickListener {
            searchList.clear()
            cancel.visibility = View.VISIBLE
            val name = search_bar.text.toString()
            val bookmark = GetAllBookmarks(this@MyBookmarks).execute().get()
            for(i in bookmark.indices) {
                if(bookmark[i].bookmarkName.contains(name, ignoreCase = true)||bookmark[i].siteUrl.contains(name, ignoreCase = true)){
                    searchList.add(bookmark[i])
                }
            }
            if (searchList.isEmpty()) {
                noBookMark.visibility = View.VISIBLE
                star.visibility = View.VISIBLE
                recyclerBookmarks.visibility = View.GONE
            } else {
                recyclerAdapter = BookmarkRecyclerAdapter(this@MyBookmarks, searchList)
                recyclerBookmarks.adapter = recyclerAdapter
                recyclerBookmarks.layoutManager = layoutManager
                recyclerAdapter.notifyDataSetChanged()
                recyclerBookmarks.visibility = View.VISIBLE
                noBookMark.visibility = View.GONE
                star.visibility = View.GONE
            }
        }

        back.setOnClickListener {
            startActivity(Intent(this@MyBookmarks, MainActivity::class.java))
            finish()
        }

        cancel.setOnClickListener {
            val list = GetAllBookmarks(this@MyBookmarks).execute().get()
            if(list.isEmpty()){
                noBookMark.visibility = View.VISIBLE
                star.visibility = View.VISIBLE
                recyclerBookmarks.visibility = View.GONE
            }
            else {
                bookmarkList.clear()
                bookmarkList.addAll(list)
                recyclerAdapter = BookmarkRecyclerAdapter(this@MyBookmarks, bookmarkList)
                recyclerBookmarks.adapter = recyclerAdapter
                recyclerBookmarks.layoutManager = layoutManager
                recyclerAdapter.notifyDataSetChanged()
                noBookMark.visibility = View.GONE
                star.visibility = View.GONE
                recyclerBookmarks.visibility = View.VISIBLE
            }
        }
    }


    class GetAllBookmarks(val context: Context): AsyncTask<Void, Void, List<BookmarkEntity>>(){
        private val db = Room.databaseBuilder(context, BookmarkDatabase::class.java, "bookmark-db").build()
        override fun doInBackground(vararg params: Void?): List<BookmarkEntity> {
            return db.BookmarkDao().getAllBookmarks()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@MyBookmarks, MainActivity::class.java))
        finish()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

}