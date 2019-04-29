package com.example.yourcoastandroid;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.yourcoastandroid.AccessPointData.CCCAccPtDataStructure;
import com.example.yourcoastandroid.AccessPointData.CCCDataClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    //url for volley request
    private String locations_url = "https://api.coastal.ca.gov/access/v1/locations";
    private CCCDataClient adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);

        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        getLocations();
    }

    private void getLocations(){
        //volley string request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, locations_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //response is json object, parse using GSON
                        //GsonBuilder builder = new GsonBuilder();
                        //Gson gson = builder.create();
                        //original line with error
                        create(ListActivity.this, response);
                        //List<CCCAccPtDataStructure> list = Arrays.asList(gson.fromJson(response, CCCAccPtDataStructure[].class));

                        //ArrayList<CCCAccPtDataStructure> list = (ArrayList<CCCAccPtDataStructure>) Arrays.asList(gson.fromJson(response, CCCAccPtDataStructure[].class));
                        //adapter = new CCCDataClient(list);
                        //recyclerView.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private boolean create(Context context, String jsonString){
        String fileName = "storage.json";
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }

    }

}
