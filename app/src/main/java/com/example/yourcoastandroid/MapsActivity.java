package com.example.yourcoastandroid;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.maps.android.clustering.ClusterManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.example.yourcoastandroid.MyItem;
import com.example.yourcoastandroid.MyItemReader;

import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.example.yourcoastandroid.R.menu.menu_maps;

public class MapsActivity extends AppCompatActivity
        implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.InfoWindowAdapter,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private boolean mPermissionDenied = false;

    private GoogleMap mMap;

    private ClusterManager<MyItem> mClusterManager;

    private ClusterManager.OnClusterClickListener mClusterClickListener;

    private List<MyItem> items;

    ListView myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setContentView(R.layout.activity_search);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marina));

        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(36.6, -121.8), 5));
        CameraPosition newCamPos = new CameraPosition(new LatLng(37.2,-120.5),
                5.7f,
                map.getCameraPosition().tilt, //use old tilt
                map.getCameraPosition().bearing); //use old bearing
        map.animateCamera(CameraUpdateFactory.newCameraPosition(newCamPos), 1, null);
        enableMyLocation();
        mClusterManager = new ClusterManager<>(this, mMap);
        mMap.setOnCameraIdleListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

        //mClusterManager.setOnsetOnClusterClickListener(mClusterClickListener);
        try {
            readItems();
        } catch (JSONException e) {
            Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }


        // mMap.setOnMarkerClickListener(mClusterManager.getMarkerManager().getCollection());





    }



    private void readItems() throws JSONException {
        InputStream inputStream = getResources().openRawResource(R.raw.access_points);
        items = new MyItemReader().read(inputStream);
        mClusterManager.addItems(items);
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
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
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
        }
        return super.onOptionsItemSelected(item);

    }


    private void cameraView(){
        //CameraPosition cameraPosition = mMap.getCameraPosition();
        //testing visuals:
        ArrayList<String> stringsOfMarkers = new ArrayList<>();
        ArrayList<LatLng> latLngsOfMarkers = new ArrayList<>();
        ArrayList<Integer> IDOfMarkers = new ArrayList<>();
        VisibleRegion cameraRegion = mMap.getProjection().getVisibleRegion();
        for( MyItem item : items){

            LatLng tempItem = item.getPosition();
            if(cameraRegion.latLngBounds.contains(tempItem)) {
                latLngsOfMarkers.add(tempItem);
                stringsOfMarkers.add(item.getTitle());
                IDOfMarkers.add(item.getID());
            }
        }
        String stringOfMarkersCombined = stringsOfMarkers.toString();
        String stringofIDCombined = IDOfMarkers.toString();
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
}
