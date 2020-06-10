package com.example.shmbrowser.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookmarkDao{
    @Insert
    fun insertBookmark(bookmarks: BookmarkEntity)

    @Delete
    fun deleteBookmark(bookmarks: BookmarkEntity)

    @Query("SELECT * FROM bookmarks" )
    fun getAllBookmarks(): List<BookmarkEntity>

    @Query("SELECT * FROM bookmarks WHERE bookmarkName like :name")
    fun getByName(name: String): List<BookmarkEntity>
}