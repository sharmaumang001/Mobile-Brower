package com.sharmaumang001.srpbrowser.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.sharmaumang001.srpbrowser.Database.DatabaseHelper;
import com.sharmaumang001.srpbrowser.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyDownloadActivity extends AppCompatActivity {
    DatabaseHelper mydb;
    ListView lview;
    ListAdapter lviewAdapter;
    ArrayAdapter adapter;
    ArrayList<HashMap<String,String>> downlist;
    LinearLayout emptylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_download);
        mydb = new DatabaseHelper(this);
        lview = findViewById(R.id.downloadlistview);
        emptylist = findViewById(R.id.emptyList);
        emptylist.setVisibility(View.GONE);
        getdata();
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = lview.getAdapter().getItem(i);
                if(o instanceof Map)
                {
                    Map map = (Map) o;
                    try {
                        String filename = String.valueOf(map.get("Title"));
                        String extension = filename.substring(filename.lastIndexOf(".")+1);
                        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                        File file = new File(Environment.getExternalStorageDirectory() + "/" +
                                Environment.DIRECTORY_DOWNLOADS + "/" + filename);
                        Uri filepath = Uri.parse(String.valueOf(file));
                        Intent in = new Intent(Intent.ACTION_VIEW);
                        in.setDataAndType(filepath,type);
                        in.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        in.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(Intent.createChooser(in,"Open With: "));
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(MyDownloadActivity.this, e.getMessage()+"", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void getdata()
    {
        downlist = mydb.ShowdataDownload();
        if(downlist.isEmpty())
        {
            emptylist.setVisibility(View.VISIBLE);
            return;
        }
        lviewAdapter = new SimpleAdapter(MyDownloadActivity.this,downlist,R.layout.download_custom_list,
                new String[]{"Id_download","Title","Time","Path"},
                new int[]{R.id.customiddownload,R.id.customtitledownlaod,
                        R.id.customtimedownload});
        lview.setAdapter(lviewAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MyDownloadActivity.this, MainActivity.class);
        startActivity(intent);
    }
}