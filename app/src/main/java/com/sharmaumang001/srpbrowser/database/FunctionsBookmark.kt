package com.sharmaumang001.srpbrowser.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room

interface FunctionsBookmark {
    class DBAsyncTask(val context: Context, private val bookmark: BookmarkEntity, private val mode: Int) : AsyncTask<Void, Void, Boolean>() {
        private val db = Room.databaseBuilder(context, BookmarkDatabase::class.java, "bookmark-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when (mode) {
                1 -> {
                    db.BookmarkDao().insertBookmark(bookmark)
                    db.close()
                    return true
                }

                2 -> {
                    db.BookmarkDao().deleteBookmark(bookmark)
                    db.close()
                    return true
                }
            }
            return false
        }

    }

    class Delete(val context: Context) : AsyncTask<Void, Void, Boolean>() {
        private val db = Room.databaseBuilder(context, BookmarkDatabase::class.java, "bookmark-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            db.BookmarkDao().deleteAll()
            db.close()
            return true
        }

    }
}