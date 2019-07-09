package com.example.yourcoastandroid;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterActivity extends AppCompatActivity {
    Switch feeSwitch;
    ImageView moneyImage;

    Switch parkingSwitch;
    ImageView parkingImage;

    private List<FilterItem> items = new ArrayList<>();
    private List<FilterItem> filteredList = new ArrayList<>();
    public HashMap<String, Integer> switchChecker = new HashMap<>();

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mPermissionDenied = false;
    public Location userCurrentLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        viewById();
        setHashMap();
        getUserLocation();

        feeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked==true){
                    switchChecker.put("No Fees",1);
                    filterList(switchChecker);

                }
                else{
                    switchChecker.put("No Fees",0);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id
        }
    }

    public void viewById() {
        moneyImage = findViewById(R.id.Money);
        feeSwitch = findViewById(R.id.feeSwitch);
        feeSwitch.setChecked(false);

        parkingImage = findViewById(R.id.Parking);
        parkingSwitch = findViewById(R.id.parkingSwitch);
        parkingSwitch.setChecked(false);
    }

    //Populates the hashmap with corresponding switches and check values
    public void setHashMap(){
        switchChecker.put("No Fees",0);
        switchChecker.put("Parking",0);
    }

    public void filterList(HashMap<String,Integer> checker){
        if(items.isEmpty())
            Log.d("Items Array","Items is empty, check the getUserLocation()");
        else {
            addFilters();
            logCheck();
            Collections.sort(filteredList);
        }
    }

    public void addFilters(){
        for(Map.Entry<String,Integer> entry : switchChecker.entrySet()){
            if(entry.getKey()=="No Fees" && entry.getValue()==1){
                for (FilterItem item : items) {
                    if (item.getFee().toLowerCase().contains("no"))
                        filteredList.add(item);
                }
            }
        }
    }

    public void logCheck(){
        try {
            if(filteredList.isEmpty())
                Log.d("Null","FilterList is empty");
            else {
                for (FilterItem filter : filteredList)
                    Log.d("LocationsFees", "filterList: " + filter.getName());
            }
        }catch(NullPointerException e){System.out.println("NullPointerException triggered");}
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
        InputStream inputStream = getResources().openRawResource(R.raw.access_points);
        items = new FilterItemReader(location).read(inputStream);
    }
}

