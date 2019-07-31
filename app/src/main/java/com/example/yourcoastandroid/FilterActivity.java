package com.example.yourcoastandroid;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.yourcoastandroid.R.menu.menu_filters;

public class FilterActivity extends AppCompatActivity implements Serializable {
    Switch feeSwitch, parkingSwitch, disabledSwitch,
            bluffSwitch, tidePoolsSwitch, bikePathSwitch, visitorCenterSwitch, restroomSwitch,
            picnicAreaSwitch, dogFriendlySwitch, campGroundSwitch, strollerFriendlySwitch,
            volleyballSwitch, sandyBeachSwitch, rockyShoreSwitch, stairsToBeachSwitch,
            pathToBeachSwitch, bluffTopTrailsSwitch, bluffTopParkSwitch, dunesSwitch, fishingSwitch,
            wildlifeViewingSwitch, boatingSwitch;

    private List<FilterItem> items = new ArrayList<>();
    private transient ArrayList<Integer> filteredList = new ArrayList<>();
    private transient List<Integer> dupFreeList = new ArrayList<>();
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
        parkingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
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
        feeSwitch = findViewById(R.id.feeSwitch);
        feeSwitch.setChecked(false);

        parkingSwitch = findViewById(R.id.parkingSwitch);
        parkingSwitch.setChecked(false);

        disabledSwitch = findViewById(R.id.disAccessSwitch);
        disabledSwitch.setChecked(false);

        bluffSwitch = findViewById(R.id.bluffSwitch);
        bluffSwitch.setChecked(false);

        tidePoolsSwitch = findViewById(R.id.tidepoolSwitch);
        tidePoolsSwitch.setChecked(false);

        bikePathSwitch = findViewById(R.id.bikePathSwitch);
        bikePathSwitch.setChecked(false);

        visitorCenterSwitch = findViewById(R.id.visitorCenterSwitch);
        visitorCenterSwitch.setChecked(false);

        restroomSwitch = findViewById(R.id.restroomsSwitch);
        restroomSwitch.setChecked(false);

        picnicAreaSwitch = findViewById(R.id.picnicSwitch);
        picnicAreaSwitch.setChecked(false);

        dogFriendlySwitch = findViewById(R.id.petSwitch);
        dogFriendlySwitch.setChecked(false);

        campGroundSwitch = findViewById(R.id.campSwitch);
        campGroundSwitch.setChecked(false);

        strollerFriendlySwitch = findViewById(R.id.strollerSwitch);
        strollerFriendlySwitch.setChecked(false);

        volleyballSwitch = findViewById(R.id.volleyballSwitch);
        volleyballSwitch.setChecked(false);

        sandyBeachSwitch = findViewById(R.id.sandyBeachSwitch);
        sandyBeachSwitch.setChecked(false);

        rockyShoreSwitch = findViewById(R.id.rockyShoreSwitch);
        rockyShoreSwitch.setChecked(false);

        stairsToBeachSwitch = findViewById(R.id.stairsSwitch);
        stairsToBeachSwitch.setChecked(false);

        pathToBeachSwitch = findViewById(R.id.pathSwitch);
        pathToBeachSwitch.setChecked(false);

        bluffTopTrailsSwitch = findViewById(R.id.blufftopTrailsSwitch);
        bluffTopTrailsSwitch.setChecked(false);

        bluffTopParkSwitch = findViewById(R.id.blufftopParkSwitch);
        bluffTopParkSwitch.setChecked(false);

        dunesSwitch = findViewById(R.id.dunesSwitch);
        dunesSwitch.setChecked(false);

        fishingSwitch = findViewById(R.id.fishingSwitch);
        fishingSwitch.setChecked(false);

        wildlifeViewingSwitch = findViewById(R.id.wildLifeSwitch);
        wildlifeViewingSwitch.setChecked(false);

        boatingSwitch = findViewById(R.id.boatingSwitch);
        boatingSwitch.setChecked(false);
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
            if(item.getParking().contains("Yes")){
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

