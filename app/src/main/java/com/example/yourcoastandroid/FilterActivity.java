package com.example.yourcoastandroid;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.yourcoastandroid.R.menu.menu_filters;

public class FilterActivity extends AppCompatActivity implements Serializable {
    Switch feeSwitch;
    ImageView moneyImage;

    Switch parkingSwitch;
    ImageView parkingImage;

    private List<FilterItem> items = new ArrayList<>();
    private transient ArrayList<Integer> filteredList = new ArrayList<>();
    private List<MyItem> testFilter = new ArrayList<>();
    public HashMap<String, Integer> switchChecker = new HashMap<>();

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mPermissionDenied = false;
    public Location userCurrentLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        viewById();
        getUserLocation();

        feeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                addFilters();
                logCheck();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Add items to action bar
        getMenuInflater().inflate(menu_filters, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.apply_action:
                Intent intent = new Intent(FilterActivity.this, MapsActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("list",filteredList);
                intent.putIntegerArrayListExtra("filter",filteredList);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void viewById() {
        moneyImage = findViewById(R.id.Money);
        feeSwitch = findViewById(R.id.feeSwitch);
        feeSwitch.setChecked(false);

        parkingImage = findViewById(R.id.Parking);
        parkingSwitch = findViewById(R.id.parkingSwitch);
        parkingSwitch.setChecked(false);
    }

    public void addFilters(){
        for(FilterItem item : items){
            if(item.getFee().contains("No")){
                for(MyItem myItem : testFilter) {
                    if(item.getName().contains(myItem.getName())){
                        filteredList.add(myItem.getID());
                    }
                }
            }
        }
    }

    //Checks if filteredList is getting the correct elements
    public void logCheck(){
        try {
            if(filteredList.isEmpty())
                Log.d("Null","FilterList is empty");
            else {
                for (int i=0; i< filteredList.size();i++)
                    Log.d("LocationsFees", "filterList: " + filteredList.size() + " Items List: " + items.size());
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
                                readItemsF(currentLocation);
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
        testFilter = new MyItemReader(location).read(inputStream);
    }
    private void readItemsF(Location location) throws JSONException {
        InputStream inputStream = getResources().openRawResource(R.raw.access_points);
        items = new FilterItemReader(location).read(inputStream);
    }
}

