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
import java.util.stream.Collectors;

import static com.example.yourcoastandroid.R.menu.menu_filters;

public class FilterActivity extends AppCompatActivity implements Serializable {
    Switch feeSwitch;
    ImageView moneyImage;

    Switch parkingSwitch;
    ImageView parkingImage;

    Switch disabledSwitch;
    Switch bluffSwitch;
    Switch tidepoolesSwitch;
    Switch bikeSwitch;
    Switch visitorSwitch;
    Switch restroomsSwitch;
    Switch picnicSwitch;
    Switch petSwitch;
    Switch campgroundSwitch;
    Switch strollerSwitch;
    Switch volleyballSwitch;








    boolean feeSwitched;
    boolean parkingSwitched;
    boolean disabledSwitched;
    boolean bluffSwitched;
    boolean tidepoolesSwitched;
    boolean bikeSwitched;
    boolean visitorSwitched;
    boolean restroomsSwitched;
    boolean picnicSwitched;
    boolean petSwitched;
    boolean campgroundSwitched;
    boolean strollerSwitched;
    boolean volleyballSwitched;





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
                feeSwitched = isChecked;
            }
        });
        parkingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                parkingSwitched = isChecked;
            }
        });
        disabledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                disabledSwitched = isChecked;
            }
        });
        bluffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                bluffSwitched = isChecked;
            }
        });tidepoolesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                tidepoolesSwitched = isChecked;
            }
        });bikeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                bikeSwitched = isChecked;
            }
        });visitorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                visitorSwitched = isChecked;
            }
        });restroomsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                restroomsSwitched = isChecked;
            }
        });picnicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                picnicSwitched = isChecked;
            }
        });petSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                petSwitched = isChecked;
            }
        });
        campgroundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                campgroundSwitched = isChecked;
            }
        });strollerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                strollerSwitched = isChecked;
            }
        });volleyballSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                volleyballSwitched = isChecked;
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

                addFilters();
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

        disabledSwitch = findViewById(R.id.disAccessSwitch);
        disabledSwitch.setChecked(false);

        bluffSwitch = findViewById(R.id.bluffSwitch);
        bluffSwitch.setChecked(false);

        tidepoolesSwitch = findViewById(R.id.tidepoolSwitch);
        tidepoolesSwitch.setChecked(false);

        bikeSwitch = findViewById(R.id.bikePathSwitch);
        bikeSwitch.setChecked(false);

        visitorSwitch = findViewById(R.id.visitorCenterSwitch);
        visitorSwitch.setChecked(false);

        restroomsSwitch = findViewById(R.id.restroomsSwitch);
        restroomsSwitch.setChecked(false);
        picnicSwitch = findViewById(R.id.picnicSwitch);
        picnicSwitch.setChecked(false);
        petSwitch = findViewById(R.id.petSwitch);
        petSwitch.setChecked(false);
        campgroundSwitch = findViewById(R.id.campSwitch);
        campgroundSwitch.setChecked(false);
        strollerSwitch = findViewById(R.id.strollerSwitch);
        strollerSwitch.setChecked(false);
        volleyballSwitch = findViewById(R.id.volleyballSwitch);
        volleyballSwitch.setChecked(false);











    }

    public void addFilters(){
        for(FilterItem item : items){
            boolean addToApply = true;
            if(feeSwitched && item.getFee().contains("Yes")){
                for(MyItem myItem : testFilter) {
                    if(item.getName().contains(myItem.getName())){
                        addToApply = false;
                    }
                }
            }
            if(parkingSwitched && !item.getParking().contains("Yes")){
                for(MyItem myItem : testFilter) {
                    if(item.getName().contains(myItem.getName())){
                        addToApply = false;
                    }
                }
            }
            if(disabledSwitched && !item.getDisabled().contains("Yes")){
                for(MyItem myItem : testFilter) {
                    if(item.getName().contains(myItem.getName())){
                        addToApply = false;
                    }
                }
            }
            if(bluffSwitched && !item.getBluff().contains("Yes")){
                for(MyItem myItem : testFilter) {
                    if(item.getName().contains(myItem.getName())){
                        addToApply = false;
                    }
                }
            }
            if(tidepoolesSwitched && !item.getTidepooles().contains("Yes")){
                for(MyItem myItem : testFilter) {
                    if(item.getName().contains(myItem.getName())){
                        addToApply = false;
                    }
                }
            }
            if(bikeSwitched && !item.getBike().contains("Yes")){
                for(MyItem myItem : testFilter) {
                    if(item.getName().contains(myItem.getName())){
                        addToApply = false;
                    }
                }
            }
            if(visitorSwitched && !item.getVisitor().contains("Yes")){
                for(MyItem myItem : testFilter) {
                    if(item.getName().contains(myItem.getName())){
                        addToApply = false;
                    }
                }
            }
            if(restroomsSwitched && !item.getRestrooms().contains("Yes")){
                for(MyItem myItem : testFilter) {
                    if(item.getName().contains(myItem.getName())){
                        addToApply = false;
                    }
                }
            }
            if(picnicSwitched && !item.getPicnic().contains("Yes")){
                for(MyItem myItem : testFilter) {
                    if(item.getName().contains(myItem.getName())){
                        addToApply = false;
                    }
                }
            }
            if(petSwitched && !item.getPet().contains("Yes")){
                for(MyItem myItem : testFilter) {
                    if(item.getName().contains(myItem.getName())){
                        addToApply = false;
                    }
                }
            }
            if(campgroundSwitched && !item.getCampground().contains("Yes")){
                for(MyItem myItem : testFilter) {
                    if(item.getName().contains(myItem.getName())){
                        addToApply = false;
                    }
                }
            }
            if(strollerSwitched && !item.getStroller().contains("Yes")){
                for(MyItem myItem : testFilter) {
                    if(item.getName().contains(myItem.getName())){
                        addToApply = false;
                    }
                }
            }
            if(volleyballSwitched && !item.getVolleyball().contains("Yes")){
                for(MyItem myItem : testFilter) {
                    if(item.getName().contains(myItem.getName())){
                        addToApply = false;
                    }
                }
            }
            if(addToApply){
                filteredList.add(item.getID());
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

