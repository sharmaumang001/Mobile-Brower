package com.sharmaumang001.srpbrowser.Database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room

interface Functions {
    class DBAsyncTask(val context: Context, val bookmark: BookmarkEntity, val mode: Int): AsyncTask<Void, Void, Boolean>(){
        val db = Room.databaseBuilder(context, BookmarkDatabase::class.java, "bookmark-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when(mode){
                1 -> {
                    db.BookmarkDao().insertBookmark(bookmark)
                    return true
                }

                2 -> {
                    db.BookmarkDao().deleteBookmark(bookmark)
                    return true
                }

            }
            return false
        }

    }

    class delete(val context: Context): AsyncTask<Void, Void, Boolean>(){
        val db = Room.databaseBuilder(context, BookmarkDatabase::class.java, "bookmark-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            db.BookmarkDao().deleteAll()
            return true
        }

    }
}