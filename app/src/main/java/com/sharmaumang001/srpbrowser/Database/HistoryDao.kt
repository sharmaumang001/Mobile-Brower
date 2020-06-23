package com.sharmaumang001.srpbrowser.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {
    @Insert
    fun insert(history: HistoryEntity)

    @Delete
    fun delete(history: HistoryEntity)

    @Query("select * from history")
    fun getAll(): List<HistoryEntity>

    @Query("select count(Id) from history")
    fun count(): Int

    @Query("Delete from history")
    fun clearData()
}