package com.yourcoast.yourcoastandroid;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yourcoast.yourcoastandroid.R.menu.menu_filters;

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
    Switch sandySwitch;
    Switch rockySwitch;
    Switch stairSwitch;
    Switch pathSwitch;
    Switch bluffTrailSwitch;
    Switch bluffParkSwitch;
    Switch dunesSwitch;
    Switch fishingSwitch;
    Switch wildlifeSwitch;
    Switch boatingSwitch;










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
    boolean sandySwitched;
    boolean rockySwitched;
    boolean stairSwitched;
    boolean pathSwitched;
    boolean bluffTrailSwitched;
    boolean bluffParkSwitched;
    boolean dunesSwitched;
    boolean fishingSwitched;
    boolean wildlifeSwitched;
    boolean boatingSwitched;






    private List<FilterItem> items = new ArrayList<>();
    private transient ArrayList<Integer> filteredList = new ArrayList<>();
    boolean[] filterset  = new boolean[24];
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
        filterset = getIntent().getBooleanArrayExtra("filtersused");
        if(filterset[0]){
            feeSwitched = true;
            feeSwitch.setChecked(true);
        }
        if(filterset[1]){
            parkingSwitched = true;
            parkingSwitch.setChecked(true);

        }
        if(filterset[2]){
            disabledSwitched = true;
            disabledSwitch.setChecked(true);

        }
        if(filterset[3]){
            bluffSwitched = true;
            bluffSwitch.setChecked(true);
        }
        if(filterset[4]){
            tidepoolesSwitched = true;
            tidepoolesSwitch.setChecked(true);
        }
        if(filterset[5]){
            bikeSwitched = true;
            bikeSwitch.setChecked(true);
        }
        if(filterset[6]){
            visitorSwitched = true;
            visitorSwitch.setChecked(true);
        }
        if(filterset[7]){
            restroomsSwitched = true;
            restroomsSwitch.setChecked(true);
        }
        if(filterset[8]){
            picnicSwitched = true;
            picnicSwitch.setChecked(true);
        }
        if(filterset[9]){
            petSwitched = true;
            petSwitch.setChecked(true);
        }
        if(filterset[10]){
            campgroundSwitched = true;
            campgroundSwitch.setChecked(true);
        }if(filterset[11]){
            strollerSwitched = true;
            strollerSwitch.setChecked(true);
        }if(filterset[12]){
            volleyballSwitched = true;
            volleyballSwitch.setChecked(true);
        }if(filterset[13]){
            sandySwitched = true;
            sandySwitch.setChecked(true);
        }if(filterset[14]){
            stairSwitched = true;
            stairSwitch.setChecked(true);
        }
        if(filterset[15]){
            pathSwitched = true;
            pathSwitch.setChecked(true);
        }
        if(filterset[16]){
            rockySwitched = true;
            rockySwitch.setChecked(true);
        }
        if(filterset[17]){
            bluffTrailSwitched = true;
            bluffTrailSwitch.setChecked(true);
        }
        if(filterset[18]){
            bluffParkSwitched = true;
            bluffParkSwitch.setChecked(true);
        }if(filterset[19]){
            fishingSwitched = true;
            fishingSwitch.setChecked(true);
        }if(filterset[20]){
            tidepoolesSwitched = true;
            tidepoolesSwitch.setChecked(true);
        }if(filterset[21]){
            dunesSwitched = true;
            dunesSwitch.setChecked(true);
        }
        if(filterset[22]){
            wildlifeSwitched = true;
            wildlifeSwitch.setChecked(true);
        }
        if(filterset[23]){
            boatingSwitched = true;
            boatingSwitch.setChecked(true);
        }














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
        sandySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                sandySwitched = isChecked;
            }
        });
        stairSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                stairSwitched = isChecked;
            }
        });
        pathSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                pathSwitched = isChecked;
            }
        });
        rockySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                rockySwitched = isChecked;
            }
        });
        bluffTrailSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                bluffTrailSwitched = isChecked;
            }
        });
        bluffParkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                bluffParkSwitched = isChecked;
            }
        });
        fishingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                fishingSwitched = isChecked;
            }
        });
        tidepoolesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                tidepoolesSwitched = isChecked;
            }
        });
        dunesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                dunesSwitched = isChecked;
            }
        });
        wildlifeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                wildlifeSwitched = isChecked;
            }
        });
        boatingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                boatingSwitched = isChecked;
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


                filterset[0] = feeSwitched;
                filterset[1] = parkingSwitched;
                filterset[2] = disabledSwitched;
                filterset[3] = bluffSwitched;
                filterset[4] = tidepoolesSwitched;
                filterset[5] = bikeSwitched;
                filterset[6] = visitorSwitched;
                filterset[7] = restroomsSwitched;
                filterset[8] = picnicSwitched;
                filterset[9] = petSwitched;
                filterset[10] = campgroundSwitched;
                filterset[11] = strollerSwitched;
                filterset[12] = volleyballSwitched;
                filterset[13] = sandySwitched;
                filterset[14] = stairSwitched;
                filterset[15] = pathSwitched;
                filterset[16] = rockySwitched;
                filterset[17] = bluffTrailSwitched;
                filterset[18] = bluffParkSwitched;
                filterset[19] = fishingSwitched;
                filterset[20] = tidepoolesSwitched;
                filterset[21] = dunesSwitched;
                filterset[22] = wildlifeSwitched;
                filterset[23] = boatingSwitched;



                intent.putExtra("filtersused", filterset);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void viewById() {
        moneyImage = findViewById(R.id.Money);
        feeSwitch = findViewById(R.id.feeSwitch);
        parkingImage = findViewById(R.id.Parking);
        parkingSwitch = findViewById(R.id.parkingSwitch);
        disabledSwitch = findViewById(R.id.disAccessSwitch);
        bluffSwitch = findViewById(R.id.bluffSwitch);
        tidepoolesSwitch = findViewById(R.id.tidepoolSwitch);
        bikeSwitch = findViewById(R.id.bikePathSwitch);
        visitorSwitch = findViewById(R.id.visitorCenterSwitch);
        restroomsSwitch = findViewById(R.id.restroomsSwitch);
        picnicSwitch = findViewById(R.id.picnicSwitch);
        petSwitch = findViewById(R.id.petSwitch);
        campgroundSwitch = findViewById(R.id.campSwitch);
        strollerSwitch = findViewById(R.id.strollerSwitch);
        volleyballSwitch = findViewById(R.id.volleyballSwitch);
        sandySwitch = findViewById(R.id.sandyBeachSwitch);
        rockySwitch = findViewById(R.id.rockyShoreSwitch);
        stairSwitch = findViewById(R.id.stairsSwitch);
        pathSwitch = findViewById(R.id.pathSwitch);
        bluffTrailSwitch = findViewById(R.id.blufftopTrailsSwitch);
        bluffParkSwitch = findViewById(R.id.blufftopParkSwitch);
        dunesSwitch = findViewById(R.id.dunesSwitch);
        fishingSwitch = findViewById(R.id.fishingSwitch);
        wildlifeSwitch = findViewById(R.id.wildLifeSwitch);
        boatingSwitch = findViewById(R.id.boatingSwitch);












    }

    public void addFilters(){
        for(FilterItem item : items){
            boolean addToApply = true;
            if(feeSwitched && item.getFee().contains("Yes")){
                        addToApply = false;
            }
            if(parkingSwitched && !item.getParking().contains("Yes")){
                        addToApply = false;
            }
            if(disabledSwitched && !item.getDisabled().contains("Yes")){
                        addToApply = false;
            }
            if(bluffSwitched && !item.getBluff().contains("Yes")){
                        addToApply = false;
            }
            if(tidepoolesSwitched && !item.getTidepooles().contains("Yes")){
                        addToApply = false;
            }
            if(bikeSwitched && !item.getBike().contains("Yes")){
                        addToApply = false;
            }
            if(visitorSwitched && !item.getVisitor().contains("Yes")){
                        addToApply = false;
            }
            if(restroomsSwitched && !item.getRestrooms().contains("Yes")){
                        addToApply = false;
            }
            if(picnicSwitched && !item.getPicnic().contains("Yes")){
                        addToApply = false;
            }
            if(petSwitched && !item.getPet().contains("Yes")){
                        addToApply = false;
            }
            if(campgroundSwitched && !item.getCampground().contains("Yes")){
                        addToApply = false;
            }
            if(strollerSwitched && !item.getStroller().contains("Yes")){
                        addToApply = false;
            }
            if(volleyballSwitched && !item.getVolleyball().contains("Yes")){
                        addToApply = false;
            }
            if(sandySwitched && !item.getSandy().contains("Yes")){
                        addToApply = false;
            }
            if(rockySwitched && !item.getRocky().contains("Yes")){
                        addToApply = false;
            }
            if(stairSwitched && !item.getStair().contains("Yes")){
                        addToApply = false;
            }
            if(pathSwitched && !item.getPath().contains("Yes")){
                        addToApply = false;
            }
            if(bluffTrailSwitched && !item.getBluffTrail().contains("Yes")){
                        addToApply = false;
            }
            if(bluffParkSwitched && !item.getBluffPark().contains("Yes")){
                        addToApply = false;
            }
            if(dunesSwitched && !item.getDunes().contains("Yes")){
                        addToApply = false;
            }
            if(fishingSwitched && !item.getFishing().contains("Yes")){
                        addToApply = false;
            }
            if(wildlifeSwitched && !item.getWildlife().contains("Yes")){
                        addToApply = false;
            }
            if(boatingSwitched && !item.getBoating().contains("Yes")){
                        addToApply = false;
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
        testFilter = new DatabaseUtil(location).Read(this);
    }
    private void readItemsF(Location location) throws JSONException {
        InputStream inputStream = getResources().openRawResource(R.raw.access_points);
        items = new FilterItemReader(location).read(inputStream);
    }
}

