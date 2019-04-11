package com.example.yourcoastandroid;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

            details = detailItemReader.read(id, inputStream );

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        centerTitle();
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        System.out.println(details.RESTROOMS);
        TextView textViewToChange = (TextView) findViewById(R.id.address);
        textViewToChange.setText(details.LocationMobileWeb.toString());

        setTitle(details.NameMobileWeb.toString());

        if(details.PHONE_NMBR.equals("")){
            TextView item =  findViewById(R.id.phonelabel);
            ((ViewManager)item.getParent()).removeView(item);
            Button button = (Button) findViewById(R.id.callbutton);
            button.setVisibility(View.GONE);
        } else {
            textViewToChange = (TextView) findViewById(R.id.phone);
            textViewToChange.setText(details.PHONE_NMBR.toString());
        }


        if(!details.FEE){
            RelativeLayout item =  findViewById(R.id.FeeLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.PARKING){
            RelativeLayout item =  findViewById(R.id.ParkingLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.DSABLDACSS){
            RelativeLayout item =  findViewById(R.id.DisablityLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.RESTROOMS){
            RelativeLayout item =  findViewById(R.id.RestroomLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.VISTOR_CTR){
            RelativeLayout item =  findViewById(R.id.VisitorLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.DOG_FRIENDLY){
            RelativeLayout item =  findViewById(R.id.DogLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.EZ4STROLLERS){
            RelativeLayout item =  findViewById(R.id.StrollerLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.PCNC_AREA){
            RelativeLayout item =  findViewById(R.id.PicnicLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.CAMPGROUND){
            RelativeLayout item =  findViewById(R.id.CampLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.SNDY_BEACH){
            RelativeLayout item =  findViewById(R.id.SandLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.DUNES){
            RelativeLayout item =  findViewById(R.id.DuneLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.RKY_SHORE){
            RelativeLayout item =  findViewById(R.id.RockyLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.BLUFF){
            RelativeLayout item =  findViewById(R.id.BluffLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.STRS_BEACH){
            RelativeLayout item =  findViewById(R.id.StairsLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.PTH_BEACH){
            RelativeLayout item =  findViewById(R.id.PathLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.BLFTP_TRLS){
            RelativeLayout item =  findViewById(R.id.BlufftopTrailLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.BLFTP_PRK){
            RelativeLayout item =  findViewById(R.id.BlufftopParkLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.WLDLFE_VWG){
            RelativeLayout item =  findViewById(R.id.WildlifeLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.TIDEPOOL){
            RelativeLayout item =  findViewById(R.id.TidepoolLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.VOLLEYBALL){
            RelativeLayout item =  findViewById(R.id.VolleyballLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.FISHING){
            RelativeLayout item =  findViewById(R.id.FishingLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        if(!details.BOATING){
            RelativeLayout item =  findViewById(R.id.BoatingLayout);
            ((ViewManager)item.getParent()).removeView(item);
        }
        TextView scroll =  findViewById(R.id.photos);
        ((ViewManager)scroll.getParent()).removeView(scroll);
        if(details.Photo_1.equals("")){
            ImageView item =  findViewById(R.id.photo1);
            ((ViewManager)item.getParent()).removeView(item);
        } else {
            ImageView item =  findViewById(R.id.photo1);
            item.setImageDrawable(LoadImageFromWebOperations(details.Photo_1));
            System.out.println("Photo1");
        }
        if(details.Photo_2.equals("")){
            ImageView item =  findViewById(R.id.photo2);
            ((ViewManager)item.getParent()).removeView(item);
        } else {
            ImageView item =  findViewById(R.id.photo2);
            item.setImageURI(Uri.parse(details.Photo_2));
        }
        if(details.Photo_3.equals("")){
            ImageView item =  findViewById(R.id.photo3);
            ((ViewManager)item.getParent()).removeView(item);
        } else {
            ImageView item =  findViewById(R.id.photo3);
            item.setImageURI(Uri.parse(details.Photo_3));
        }
        if(details.Photo_4.equals("")){
            ImageView item =  findViewById(R.id.photo4);
            ((ViewManager)item.getParent()).removeView(item);
        } else {
            ImageView item =  findViewById(R.id.photo4);
            item.setImageURI(Uri.parse(details.Photo_4));
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
                .icon(BitmapDescriptorFactory.defaultMarker(60.0f))
                );
        mPoint.showInfoWindow();
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        CameraPosition newCamPos = new CameraPosition(new LatLng(details.LATITUDE,details.LONGITUDE),
                15.7f,
                mMap.getCameraPosition().tilt, //use old tilt
                mMap.getCameraPosition().bearing); //use old bearing
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newCamPos), 1, null);
    }
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
    public void DirectionButtonClick(View v) {
        System.out.println("Directions Button Click");
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+details.LATITUDE+","+details.LONGITUDE);
        Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);

    }
    public void CallButtonClick(View v) {
        Uri gmmIntentUri = Uri.parse("tel:" +details.PHONE_NMBR);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(gmmIntentUri);
        startActivity(intent);
    }
    public void SearchButtonClick(View v) {
        System.out.println("Search Button Click");
        String url = "http://www.google.com/search?q="+details.NameMobileWeb;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }




}
