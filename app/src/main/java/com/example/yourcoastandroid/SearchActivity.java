package com.example.yourcoastandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.yourcoastandroid.AccessPointData.CCCDataClient;


public class SearchActivity extends AppCompatActivity {

    //url for volley request
//    private String locations_url = "https://api.coastal.ca.gov/access/v1/locations";
//    private CCCDataClient adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);
    }

//    public void searchSuggestions(){
//        //ListView listView = (ListView)findViewById(R.id.myList);
//        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.search_item);
//    }
}
