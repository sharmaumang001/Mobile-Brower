package com.sharmaumang001.srpbrowser.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String Database_name = "Downloads.db";
    public static final String Table_Download = "Downloads";
    public static final String down_id = "Id_download";
    public static final String down_title = "Title";
    public static final String down_time = "Time";
    public static final String down_path = "Path";
    public DatabaseHelper(@Nullable Context context) {
        super(context, Database_name, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Table_Download +" (Id_download INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT , Time TEXT, Path TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Table_Download);
        onCreate(sqLiteDatabase);
    }
    public boolean insertDataDownload(String Title,String Time, String Path)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(down_title,Title);
        cv.put(down_time,Time);
        cv.put(down_path,Path);
        Long result = db.insert(Table_Download,null,cv);
        if(result == -1 )
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public ArrayList<HashMap<String,String>> ShowdataDownload()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String,String>> downlist = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from "+Table_Download,null);
        while(cursor.moveToNext())
        {
            HashMap<String,String> user = new HashMap<>();
            user.put("Id_download",cursor.getString(cursor.getColumnIndex(down_id)));
            user.put("Title",cursor.getString(cursor.getColumnIndex(down_title)));
            user.put("Time",cursor.getString(cursor.getColumnIndex(down_time)));
            user.put("Path",cursor.getString(cursor.getColumnIndex(down_path)));
            downlist.add(user);
        }
        return downlist;
    }

    public void deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.rawQuery("DROP TABLE "+Table_Download, null);
    }
}
