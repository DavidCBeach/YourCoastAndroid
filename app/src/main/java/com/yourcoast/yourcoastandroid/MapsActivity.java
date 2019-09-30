package com.yourcoast.yourcoastandroid;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.yourcoast.yourcoastandroid.AccessPointData.ListItemAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.google.maps.android.ui.SquareTextView;
import org.json.JSONException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import android.app.SearchManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.content.SharedPreferences;



import static com.yourcoast.yourcoastandroid.R.menu.menu_maps;

public class MapsActivity extends AppCompatActivity
        implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.InfoWindowAdapter,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        ListItemAdapter.onItemListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private boolean mPermissionDenied = false;

    private GoogleMap mMap;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    private ClusterManager<MyItem> mClusterManager;

    private ClusterManager.OnClusterClickListener mClusterClickListener;

    public Location userCurrentLocation;

    private List<MyItem> items;

    private List<MyItem> itemsInView = new ArrayList<>();

    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;
    //url for volley request
    private String locations_url = "https://api.coastal.ca.gov/access/v1/locations";

    private ListItemAdapter adapter;

    private AppCompatButton filterButton;

    private ArrayList<Integer> fList = new ArrayList<>();

    private ArrayList<MyItem> finalList = new ArrayList<>();

    private InputStream inputStream;

    private boolean[] filterset  = new boolean[24];

    SharedPreferences prefs = null;

    //CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayout);
    //LinearLayout linearLayout;
    View layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);






        //setContentView(R.layout.activity_search);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
        }

        try {
            filterset = intent.getBooleanArrayExtra("filtersused");
        }catch(NullPointerException e) {System.out.println("Serialization is null");}
        if(filterset == null){
            filterset = new boolean[24];
            Arrays.fill(filterset,Boolean.FALSE);
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        findViewById(R.id.recyclerView).setFocusable(false);
        findViewById(R.id.lintemp).requestFocus();
        //mMap.getLocation() was depricated to using getFusedLocation instead
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        //getUserLocation();

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayout);
        View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_sheet);
        //linearLayout = (LinearLayout) findViewById(R.id.lintemp);
        //layoutBottomSheet = layoutBottomSheet.findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(bottomSheet);

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        // btnBottomSheet.setText("Close Sheet");
                        Log.d("state", "expanded");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        //btnBottomSheet.setText("Expand Sheet");
                        Log.d("state", "collapsed");

                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        filterButtonActions();
    }

    private void filtering(){
        Log.d("Run Location", "I'm here at filtering()");
        try {
            Intent filterIntent = getIntent();
            fList = filterIntent.getIntegerArrayListExtra("filter");
            logCheck();
        }catch(NullPointerException e) {System.out.println("Serialization is null");}
        try{
            if( fList!=null) {
                if (!fList.isEmpty())
                    filterIdToMyItem(userCurrentLocation);
            }
        } catch (JSONException e) { e.printStackTrace(); }
    }

    private void logCheck(){
        try {
            if(fList.isEmpty() && fList!=null)
                Log.d("Null","FilterList is empty");
            else {
                for (int i=0;i<fList.size();i++)
                    Log.d("LocationsFees", "filterList: " + fList.get(i));
            }
        }catch(NullPointerException e){System.out.println("NullPointerException triggered");}
    }

    public void filterButtonActions(){
        filterButton = findViewById(R.id.roundFilterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, FilterActivity.class);
                System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
                System.out.println(filterset);
                intent.putExtra("filtersused", filterset);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        prefs = getSharedPreferences("com.yourcoast.yourcoastandroid", MODE_PRIVATE);
        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            startActivity(new Intent(this, StartActivity.class));
            prefs.edit().putBoolean("firstrun", false).commit();
        }
        final Activity activity = this;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, locations_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!prefs.getString("olddata", "").equals(response)) {
                            // Do first run stuff here then set 'firstrun' as false
                            DatabaseUtil dbu = new DatabaseUtil();
                            dbu.getLocation(response, activity);
                            prefs.edit().putString("olddata", response).commit();
                            //adapter = new ListItemAdapter(itemsInView, new ListItemAdapter());
                            recyclerView.setAdapter(adapter);
                        }
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
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Temporary Example pin
        LatLng marina = new LatLng(36.6, -121.8);
        //mMap.addMarker(new MarkerOptions().position(marina).title("Marina").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(marina));

        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(36.6, -121.8), 5));
        CameraPosition newCamPos = new CameraPosition(new LatLng(37.2,-120.5),
                5.7f,
                map.getCameraPosition().tilt, //use old tilt
                map.getCameraPosition().bearing); //use old bearing
        map.animateCamera(CameraUpdateFactory.newCameraPosition(newCamPos), 1, null);
        enableMyLocation();
        mClusterManager = new ClusterManager<>(this, mMap);
        CustomRenderer customRenderer = new CustomRenderer();
        mClusterManager.setRenderer(customRenderer);
        mClusterManager.setOnClusterClickListener(mClusterClickListener);
        mMap.setOnCameraIdleListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

        //moved to getUserLocation function to add location overload
        //mClusterManager.setOnsetOnClusterClickListener(mClusterClickListener);
//        try {
//            getUserLocation();
//           // readItems();
//        } catch (JSONException e) {
//            Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
//        }
        inputStream = getResources().openRawResource(R.raw.access_points);
        getUserLocation();

        // mMap.setOnMarkerClickListener(mClusterManager.getMarkerManager().getCollection());
    }



    private class CustomRenderer extends DefaultClusterRenderer<MyItem> {
        private final IconGenerator mIconGenerator = new IconGenerator(getApplicationContext());
        private final IconGenerator mClusterIconGenerator = new IconGenerator(getApplicationContext());
        private ShapeDrawable mColoredCircleBackground;
        private SparseArray<BitmapDescriptor> mIcons = new SparseArray<BitmapDescriptor>();
        private final float mDensity;


        public CustomRenderer() {
            super(getApplicationContext(), mMap, mClusterManager);

            mColoredCircleBackground = new ShapeDrawable(new OvalShape());
            mIconGenerator.setContentView(makeSquareTextView(getApplicationContext()));
            mIconGenerator.setTextAppearance(R.style.amu_ClusterIcon_TextAppearance);

            mIconGenerator.setBackground(makeClusterBackground());
            mDensity = getApplicationContext().getResources().getDisplayMetrics().density;

        }

        private SquareTextView makeSquareTextView(Context context) {
            SquareTextView squareTextView = new SquareTextView(context);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            squareTextView.setLayoutParams(layoutParams);
            squareTextView.setId(R.id.amu_text);
            int twelveDpi = (int) (12 * mDensity);
            squareTextView.setPadding(twelveDpi, twelveDpi, twelveDpi, twelveDpi);
            return squareTextView;
        }
        private LayerDrawable makeClusterBackground() {
            mColoredCircleBackground = new ShapeDrawable(new OvalShape());
            ShapeDrawable outline = new ShapeDrawable(new OvalShape());
            mColoredCircleBackground.setPadding(40,40,40,40);
            outline.getPaint().setColor(0x80ffffff); // Transparent white.
            LayerDrawable background = new LayerDrawable(new Drawable[]{outline, mColoredCircleBackground});
            int strokeWidth = (int) (mDensity * 3);
            background.setLayerInset(1, strokeWidth, strokeWidth, strokeWidth, strokeWidth);
            return background;
        }
        @Override
        protected int getColor(int clusterSize) {

            return Color.HSVToColor(new float[]{
                    200f, 1f, .6f
            });
        }
        @Override
        protected void onBeforeClusterItemRendered(MyItem person, MarkerOptions markerOptions) {


            //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.yellow));
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(58.0f));

        }
        @Override
        protected void onBeforeClusterRendered(Cluster<MyItem> cluster, MarkerOptions markerOptions) {
            int bucket = getBucket(cluster);
            BitmapDescriptor descriptor = mIcons.get(bucket);
            if (descriptor == null) {
                mColoredCircleBackground.getPaint().setColor(getColor(bucket));
                descriptor = BitmapDescriptorFactory.fromBitmap(mIconGenerator.makeIcon(getClusterText(bucket)));
                mIcons.put(bucket, descriptor);
            }
            markerOptions.icon(descriptor);
        }
    }


    private void readItems(Location location) throws JSONException {
        logCheck();
        if(fList != null){
            if(!fList.isEmpty()) {
                items = new DatabaseUtil(location).Read(this);
                Log.d("FinalListSize", "finalList Size: " + items.size());
                mClusterManager.addItems(items);
            }   else {
                items = new DatabaseUtil(location).Read(this);
                //creates recyclerview
                //setList();
                mClusterManager.addItems(items);
            }
        }
        else {
            items = new DatabaseUtil(location).Read(this);
            //creates recyclerview
            //setList();
            mClusterManager.addItems(items);
        }
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {
        //cameraView(marker);
        try {

            String id = "";
            if (marker.getSnippet() == null) {
                //Toast.makeText(this,marker.getTag().toString() ,Toast.LENGTH_LONG).show();
                id = marker.getTag().toString();
            } else {
                //Toast.makeText(this,marker.getSnippet() ,Toast.LENGTH_LONG).show();
                marker.setTag(marker.getSnippet());
                id = marker.getSnippet();
            }
            marker.setSnippet(null);


        } catch(Exception e) {
            System.out.println("cluster click");
        }
        return false;
    }
    /*This is to launch the Details activity*/
    private void launchDetails(String id){
        Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
        intent.putExtra("DATA_ID", id);
        startActivity(intent);
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        //cameraView();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            enableMyLocation();
        } else {
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.RationaleDialog.newInstance(LOCATION_PERMISSION_REQUEST_CODE, true)
                .show(this.getSupportFragmentManager(), "dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Add items to action bar
        getMenuInflater().inflate(menu_maps, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Is item is selected, if so do correlated action
        switch(item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);

                return true;

            case R.id.action_info:
                startActivity(new Intent(this, InfoActivity.class));

                return true;

            case R.id.search_action:
                startActivity(new Intent(this, SearchActivity.class));

                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    //ArrayList<Integer> IDOfMarkers = new ArrayList<>();

    private void cameraView(){
        //CameraPosition cameraPosition = mMap.getCameraPosition();
        //testing visuals:
        if(itemsInView != null)
            itemsInView.clear();
        ArrayList<String> stringsOfMarkers = new ArrayList<>();
        ArrayList<LatLng> latLngsOfMarkers = new ArrayList<>();
        VisibleRegion cameraRegion = mMap.getProjection().getVisibleRegion();

        if(items != null) {
            for (MyItem item : items) {

                LatLng tempItem = item.getPosition();
                if (cameraRegion.latLngBounds.contains(tempItem)) {
                    latLngsOfMarkers.add(tempItem);
                    stringsOfMarkers.add(item.getTitle());
                    itemsInView.add(item);
                }
            }
            if (itemsInView.size() == 0) {
                //BottomSheetFragment fragment = new BottomSheetFragment();
                //fragment.setCancelable(false);

                //fragment.show(getSupportFragmentManager(), "TAG");

            }
        }

        setList();
        //Log.d("idofmarkers", IDOfMarkers.toString());
        //String stringOfMarkersCombined = stringsOfMarkers.toString();
        //String stringofIDCombined = IDOfMarkers.toString();
        //uncomment following lines to display proof of concept for ID list of Markers shown
        //Toast.makeText(this,stringOfMarkersCombined,Toast.LENGTH_SHORT).show();
        //Toast.makeText(this,stringofIDCombined,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraIdle() {
        cameraView();
        mClusterManager.onCameraIdle();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        try {

            String id = "";
            if (marker.getSnippet() == null) {
                //Toast.makeText(this,marker.getTag().toString() ,Toast.LENGTH_LONG).show();
                id = marker.getTag().toString();
            } else {
                //Toast.makeText(this,marker.getSnippet() ,Toast.LENGTH_LONG).show();
                marker.setTag(marker.getSnippet());
                id = marker.getSnippet();
            }
            launchDetails(id);


        } catch(Exception e) {
            System.out.println("cluster click");
        }
    }

    @Override
    public void onClick(int position) {
        String id = String.valueOf(itemsInView.get(position).getID());
        String distance = String.valueOf(itemsInView.get(position).getDistance());
        if(distance.equals("null")){
            //TODO: Fix render bug - When zoomed all the way in and then quickly zooming out, all markers render and lag the app
            CameraPosition newCamPos = new CameraPosition(new LatLng(37.2,-120.5),
                    5.7f,
                    0, //use default tilt
                    0); //use default bearing
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newCamPos), 1, null);
        }else
            launchDetails(id);
    }

    //creates list
    private void setList(){
        //TODO: Fix list bug - list doesn't seem to respect tilt/bearing, still showing some items when camera is rotated and no markers visible
        //Log.d("jList", items.toString());
        //sorts array by ascending distance
        Collections.sort(itemsInView);
        //Log.d("sorted jList", items.toString());
        adapter = new ListItemAdapter(itemsInView, this);
        recyclerView.setAdapter(adapter);
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
                            //Log.d("locationfound", currentLocation.toString());
                            //setLocation(currentLocation);
                            userCurrentLocation = currentLocation;
                            filtering();
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
    public void onBackPressed(){



        // if(recyclerView.getHeight())
        Log.d("back", "backpressed");
        if(sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        else
            super.onBackPressed();
    }


    public void filterIdToMyItem(Location location) throws JSONException{
//        MyItem item;
//        //items = new MyItemReader(location).read(inputStream);
//
//        Log.d("Running Location","I'm at the filterIdToMyItem()");
//        for(int i=0;i<fList.size();i++){
//
//            Log.d("Index Location: ", "index: " + i);
//            item = new MyItemReader(location).readByID(inputStream,fList.get(i));
//            //finalList.add(item);
//        }
    }
}


