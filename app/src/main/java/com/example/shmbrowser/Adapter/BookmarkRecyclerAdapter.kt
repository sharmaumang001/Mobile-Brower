package com.example.shmbrowser.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shmbrowser.Activity.MainActivity
import com.example.shmbrowser.Database.BookmarkEntity

import com.example.shmbrowser.R

class BookmarkRecyclerAdapter(val context: Context, val bookmarkList: List<BookmarkEntity>): RecyclerView.Adapter<BookmarkRecyclerAdapter.BookmarkViewHolder>(){
    class BookmarkViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name: TextView = view.findViewById(R.id.name)
        val url: TextView = view.findViewById(R.id.url)
        val recyclerItem: LinearLayout = view.findViewById(R.id.recyclerItem)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bookmark_single_row, parent, false)
        return BookmarkViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookmarkList.size
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val list = bookmarkList[position]
        holder.name.text = list.bookmarkName
        holder.url.text = list.siteUrl
        holder.recyclerItem.setOnClickListener {
            val intent = Intent(context , MainActivity::class.java)
            val sharedPreferences = context.getSharedPreferences("url", Context.MODE_PRIVATE)
            sharedPreferences.edit().putString("url", list.siteUrl).apply()
            context.startActivity(intent)
        }
    }
}