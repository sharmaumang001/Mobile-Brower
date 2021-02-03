package com.sharmaumang001.srpbrowser.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sharmaumang001.srpbrowser.R;
import com.sharmaumang001.srpbrowser.database.DatabaseHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class MyDownloadActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    ListView listView;
    ListAdapter listViewAdapter;
//    ArrayAdapter adapter;
    ArrayList<HashMap<String, String>> downList;
    LinearLayout emptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_download);
        myDb = new DatabaseHelper(this);
        listView = findViewById(R.id.download_list_view);
        emptyList = findViewById(R.id.emptyList);
        emptyList.setVisibility(View.GONE);
        getData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = listView.getAdapter().getItem(i);
                if (o instanceof Map) {
                    Map map = (Map) o;
                    try {
                        String filename = String.valueOf(map.get("Title"));
                        String extension = filename.substring(filename.lastIndexOf(".") + 1);
                        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                        File file = new File(Environment.getExternalStorageDirectory() + "/" +
                                Environment.DIRECTORY_DOWNLOADS + "/" + filename);
                        Uri filepath = Uri.parse(String.valueOf(file));
                        Intent in = new Intent(Intent.ACTION_VIEW);
                        in.setDataAndType(filepath, type);
                        in.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        in.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(Intent.createChooser(in, "Open With: "));
                    } catch (Exception e) {
                        Toast.makeText(MyDownloadActivity.this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void getData() {
        downList = myDb.ShowDataDownload();
        if (downList.isEmpty()) {
            emptyList.setVisibility(View.VISIBLE);
            return;
        }
        listViewAdapter = new SimpleAdapter(MyDownloadActivity.this, downList, R.layout.download_custom_list,
                new String[]{"Id_download", "Title", "Time", "Path"},
                new int[]{R.id.custom_id_download, R.id.custom_title_download,
                        R.id.custom_time_download});
        listView.setAdapter(listViewAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MyDownloadActivity.this, MainActivity.class);
        startActivity(intent);
    }
}