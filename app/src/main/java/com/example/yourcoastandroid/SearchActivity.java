package com.example.yourcoastandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class SearchActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        //ListView listView = (ListView)findViewById(R.id.myList);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.search_item);
    }


}
