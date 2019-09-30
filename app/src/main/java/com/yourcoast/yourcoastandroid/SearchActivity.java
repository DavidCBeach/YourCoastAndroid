package com.yourcoast.yourcoastandroid;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Filter;
import android.widget.SearchView;


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

//TODO: make search with descriptions being searchable
public class SearchActivity extends AppCompatActivity implements com.yourcoast.yourcoastandroid.AccessPointData.SearchItemAdapter.onItemListener{

    private List<MyItem> items;
    private List<MyItem> filteredList = new ArrayList<>();
    private ClusterManager<MyItem> mClusterManager;
    private com.yourcoast.yourcoastandroid.AccessPointData.SearchItemAdapter adapter;
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


        searchView = (SearchView) findViewById(R.id.searchView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        items = new ArrayList<>();
        getUserLocation();
        setList((ArrayList<MyItem>) items);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        findViewById(R.id.recyclerView).setFocusable(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(!searchView.isIconified()){
                    getFilter().filter(s);
                    adapter.notifyDataSetChanged();
                    return false;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    public void onClick(int position){
        String id = String.valueOf(filteredList.get(position).getID());
        launchDetails(id);
    }

    private void launchDetails(String id){
        Intent intent = new Intent(getBaseContext(),  DetailsActivity.class);
        intent.putExtra("DATA_ID", id);
        startActivity(intent);
    }

    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if(charString.isEmpty()){ filteredList = items; }
                else{
                    List<MyItem> filtered = new ArrayList<>();
                    for(MyItem item : items){
                        try {
                            //name match condition
                            if ((item.getName()+item.getDescription()).toLowerCase().contains(charString.toLowerCase())) { filtered.add(item); }
                        }catch(IndexOutOfBoundsException index){
                            throw index;
                        }
                    }
                    filteredList = filtered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<MyItem>) filterResults.values;
                setList((ArrayList<MyItem>) filteredList);
            }
        };
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
                        } else { Log.d("locationfound", "current location is NULL"); }
                    }
                });
            }}catch(SecurityException e) { Log.e("locationfound ", e.getMessage()); }
    }

    private void readItems(Location location) throws JSONException {
        items = new DatabaseUtil(location).Read(this);
    }

    //creates list
    private void setList(ArrayList<MyItem> list){
        //sorts array by ascending distance
        Collections.sort(list);
        adapter = new com.yourcoast.yourcoastandroid.AccessPointData.SearchItemAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }
}