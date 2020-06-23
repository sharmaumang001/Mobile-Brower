package com.sharmaumang001.srpbrowser.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sharmaumang001.srpbrowser.Database.FunctionsHistory
import com.sharmaumang001.srpbrowser.Database.HistoryEntity
import com.sharmaumang001.srpbrowser.R

class BrowserHistoryRecyclerAdapter(val context: Context, val historyEntity: List<HistoryEntity>): RecyclerView.Adapter<BrowserHistoryRecyclerAdapter.BrowserHistoryViewHolder>(){
    class BrowserHistoryViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val url: TextView = view.findViewById(R.id.url)
        val delete: ImageButton =view.findViewById(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowserHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_single_row, parent, false)
        return BrowserHistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return historyEntity.size
    }

    override fun onBindViewHolder(holder: BrowserHistoryViewHolder, position: Int) {
        val list = historyEntity[position]
        holder.url.text = list.url
        holder.delete.setOnClickListener {
            if(FunctionsHistory.DBAsyncTask(context, list, 2).execute().get()){
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                holder.view.visibility=View.GONE
            }
            else{
                Toast.makeText(context, "Some error occurred", Toast.LENGTH_SHORT).show()
            }
        }
    }
}