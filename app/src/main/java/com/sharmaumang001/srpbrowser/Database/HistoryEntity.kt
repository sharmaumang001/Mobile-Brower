package com.sharmaumang001.srpbrowser.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
        @PrimaryKey val Id: Int,
        @ColumnInfo(name = "url") val url: String
)