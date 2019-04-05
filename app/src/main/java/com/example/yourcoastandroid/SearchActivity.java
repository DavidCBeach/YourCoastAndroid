package com.example.yourcoastandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ListView listView = (ListView)findViewById(R.id.myList);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.search_item);

    }
}
