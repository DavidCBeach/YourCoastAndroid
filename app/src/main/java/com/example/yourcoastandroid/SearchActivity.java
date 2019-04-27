package com.example.yourcoastandroid;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.SearchView;

import com.example.yourcoastandroid.AccessPointData.SearchItemAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SearchActivity extends AppCompatActivity {

    private List<MyItem> items;
    private List<MyItem> filtered = new ArrayList<>();
    private ClusterManager<MyItem> mClusterManager;
    private SearchItemAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mPermissionDenied = false;
    public Location userCurrentLocation;
    private SearchView searchView;
    private EditText editSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//        Intent intent = getIntent();
//        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
//            adapter.getFilter().filter(query);
//        }
        searchView = (SearchView) findViewById(R.id.searchView);
        getUserLocation();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        items = new ArrayList<>();
        adapter = new SearchItemAdapter(this, items);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        findViewById(R.id.recyclerView).setFocusable(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(!searchView.isIconified()){
                    //searchView.setIconified(true);
                    adapter.getFilter().filter(s);
                    //recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    return false;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("inner", "hereinner");
                adapter.getFilter().filter(s);
                recyclerView.setAdapter(adapter);
               // adapter.notifyDataSetChanged();
                Log.d("inner","filtered");
                return false;
            }
        });
    }

    private List<MyItem> filter(List<MyItem> list, String query){

        final List<MyItem> filter = new ArrayList<>();
        int i = list.size()-1;
        if(list.size()>0) {
            for (MyItem item : list) {
                final String text = item.getName().toLowerCase();
                if (text.startsWith(query)) {
                    filter.add(item);
                    setList();
                    //Log.d("Filtering location",filter.get(i).getName().toString());
                    //                filter.remove(filter.get(i));
                    //i--;
                }
            }
        }else{throw new IndexOutOfBoundsException("Access an invalid index");}
        return filter;
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
        adapter = new SearchItemAdapter(this, items);
        recyclerView.setAdapter(adapter);
    }
}
