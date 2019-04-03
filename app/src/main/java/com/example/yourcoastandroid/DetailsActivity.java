package com.example.yourcoastandroid;

import android.app.ActionBar;
import android.content.Context;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private DetailItem details = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String id= getIntent().getStringExtra("DATA_ID");
        DetailItemReader detailItemReader = new DetailItemReader();
        InputStream inputStream = getResources().openRawResource(R.raw.access_points);
        try {
            details = detailItemReader.read( id , inputStream);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        centerTitle();
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        System.out.println(details.RESTROOMS);
        TextView textViewToChange = (TextView) findViewById(R.id.address);
        textViewToChange.setText(details.LocationMobileWeb.toString());
        textViewToChange = (TextView) findViewById(R.id.phone);
        textViewToChange.setText(details.PHONE_NMBR.toString());
        setTitle(details.NameMobileWeb.toString());


        if(!details.FEE){
            RelativeLayout item =  findViewById(R.id.FeeLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.PARKING){
            RelativeLayout item =  findViewById(R.id.ParkingLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Add items to action bar
        getMenuInflater().inflate(R.menu.menu_info, menu);

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
        }
        return super.onOptionsItemSelected(item);

    }
    private void centerTitle()
    {
        ArrayList<View> textViews = new ArrayList<>();

        getWindow().getDecorView().findViewsWithText(textViews, getTitle(), View.FIND_VIEWS_WITH_TEXT);

        if(textViews.size() > 0)
        {
            AppCompatTextView appCompatTextView = null;

            if(appCompatTextView != null)
            {
                ViewGroup.LayoutParams params = appCompatTextView.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                appCompatTextView.setLayoutParams(params);
                appCompatTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        LatLng point = new LatLng(details.LATITUDE,details.LONGITUDE);
        Marker mPoint = mMap.addMarker(new MarkerOptions()
                .position(point)
                .title(details.NameMobileWeb)
                );
        mPoint.showInfoWindow();
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        CameraPosition newCamPos = new CameraPosition(new LatLng(details.LATITUDE,details.LONGITUDE),
                15.7f,
                mMap.getCameraPosition().tilt, //use old tilt
                mMap.getCameraPosition().bearing); //use old bearing
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newCamPos), 1, null);
    }
}
