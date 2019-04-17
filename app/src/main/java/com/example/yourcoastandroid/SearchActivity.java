package com.example.yourcoastandroid;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.yourcoastandroid.AccessPointData.CCCAccPtDataStructure;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private String locations_url = "https://api.coastal.ca.gov/access/v1/locations";
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private List<CCCAccPtDataStructure> list;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        list = new ArrayList<>();
        //adapter = new RecyclerAdapter(this, list,this);

        // white background notification bar
        whiteNotificationBar(recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        try {
            getInformation();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getInformation() throws JSONException {
        //volley string request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, locations_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //response is json object, parse using GSON
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        //original line with error
                        list = Arrays.asList(gson.fromJson(response, CCCAccPtDataStructure[].class));
                        //ArrayList<CCCAccPtDataStructure> list = (ArrayList<CCCAccPtDataStructure>) Arrays.asList(gson.fromJson(response, CCCAccPtDataStructure[].class));
                        recyclerView.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.searchView) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }
}
