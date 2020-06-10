package com.example.shmbrowser.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val siteUrl: String,
    @ColumnInfo(name = "bookmarkName") val bookmarkName: String
)