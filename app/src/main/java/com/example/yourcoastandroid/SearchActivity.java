package com.example.yourcoastandroid;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;

import com.example.yourcoastandroid.AccessPointData.SearchItemAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONException;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;


public class SearchActivity extends AppCompatActivity {

    private List<MyItem> items;
    private ClusterManager<MyItem> mClusterManager;
    private SearchItemAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mPermissionDenied = false;
    public Location userCurrentLocation;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getUserLocation();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        findViewById(R.id.recyclerView).setFocusable(false);

        initQueryTextListener(searchView);
    }

    @NonNull
    private void initQueryTextListener(SearchView searchView) {

        System.out.print("Here");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // filter recycler view when query submitted
//                adapter.getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // filter recycler view when text is changed
//                adapter.getFilter().filter(newText);
//                return true;
//            }
//        });
    }

    //gets the users current location and calls json parse function
    //needed in this order to avoid location being null
    public void getUserLocation(){
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            if(!mPermissionDenied){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d("locationfound", "onComplete found location");
                            Location currentLocation = (Location) task.getResult();
                            Log.d("locationfound", currentLocation.toString());
                            //setLocation(currentLocation);
                            userCurrentLocation = currentLocation;
                            try {
                                readItems(currentLocation);
                            } catch (JSONException e) {
                                Log.e("locationfound", e.getMessage());
                                //Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
                            }
                            Log.d("locationfound", userCurrentLocation.toString());
                        } else {
                            Log.d("locationfound", "current location is NULL");
                        }
                    }
                });
            }}catch(SecurityException e) {
            Log.e("locationfound ", e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maps, menu);
        return true;
    }

    private void readItems(Location location) throws JSONException {
        InputStream inputStream = getResources().openRawResource(R.raw.access_points);
        items = new MyItemReader(location).read(inputStream);
        //creates recyclerview
        setList();
        //mClusterManager.addItems(items);
    }

    //creates list
    private void setList(){
        //Log.d("jList", items.toString());
        //sorts array by ascending distance
        Collections.sort(items);
        //Log.d("sorted jList", items.toString());
        adapter = new SearchItemAdapter(items);
        recyclerView.setAdapter(adapter);
    }
}
