package com.sharmaumang001.srpbrowser.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sharmaumang001.srpbrowser.Activity.MainActivity
import com.sharmaumang001.srpbrowser.Database.BookmarkEntity
import com.sharmaumang001.srpbrowser.Database.FunctionsBookmark
import com.sharmaumang001.srpbrowser.R

class BookmarkRecyclerAdapter(val context: Context, val bookmarkList: List<BookmarkEntity>): RecyclerView.Adapter<BookmarkRecyclerAdapter.BookmarkViewHolder>(){
    class BookmarkViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name: TextView = view.findViewById(R.id.name)
        val url: TextView = view.findViewById(R.id.url)
        val recyclerItem: LinearLayout = view.findViewById(R.id.recyclerItem)
        val delete: ImageButton = view.findViewById(R.id.delete)
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
            context.startActivity(intent)
        }
        holder.delete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete")
            builder.setMessage("Sure you want to delete bookmark?")
            builder.setPositiveButton("Delete"){text,listner ->
                val check = FunctionsBookmark.DBAsyncTask(context, bookmarkList[position], 2).execute().get()
                if(check){
                    Toast.makeText(context, "Bookmark Deleted", Toast.LENGTH_SHORT).show()
                    notifyItemRemoved(position)
                    notifyDataSetChanged()
                    holder.itemView.visibility=View.GONE;

                }
                else{
                    Toast.makeText(context, "Some error occurred", Toast.LENGTH_SHORT).show()
                }
            }
            builder.setNegativeButton("Cancle"){text, listener ->
                Toast.makeText(context, "Bookmark Not Deleted", Toast.LENGTH_SHORT).show()
            }
            builder.create()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                builder.show().getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getColor(R.color.colorPrimary))
            }

        }
    }
}