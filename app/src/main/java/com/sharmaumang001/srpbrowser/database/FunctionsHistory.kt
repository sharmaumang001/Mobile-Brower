package com.sharmaumang001.srpbrowser.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room

interface FunctionsHistory {
    class DBAsyncTask(val context: Context, private val historyEntity: HistoryEntity, private val mode: Int) : AsyncTask<Void, Void, Boolean>() {
        private val db = Room.databaseBuilder(context, HistoryDatabase::class.java, "history-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when (mode) {
                1 -> {
                    db.historyDao().insert(historyEntity)
                    db.close()
                    return true
                }

                2 -> {
                    db.historyDao().delete(historyEntity)
                    db.close()
                    return true
                }
            }
            return false
        }
    }

    class ClearData(val context: Context) : AsyncTask<Void, Void, Boolean>() {
        private val db = Room.databaseBuilder(context, HistoryDatabase::class.java, "history-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            db.historyDao().clearData()
            db.close()
            return true
        }
    }

    class Count(val context: Context) : AsyncTask<Void, Void, Int>() {
        private val db = Room.databaseBuilder(context, HistoryDatabase::class.java, "history-db").build()
        override fun doInBackground(vararg params: Void?): Int {
            return db.historyDao().count()
        }
    }

    class GetAll(val context: Context) : AsyncTask<Void, Void, List<HistoryEntity>>() {
        private val db = Room.databaseBuilder(context, HistoryDatabase::class.java, "history-db").build()
        override fun doInBackground(vararg params: Void?): List<HistoryEntity> {
            return db.historyDao().getAll()
        }
    }
}