package com.example.yourcoastandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;

import java.io.InputStream;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String id= getIntent().getStringExtra("DATA_ID");
        DetailItemReader detailItemReader = new DetailItemReader();
        InputStream inputStream = getResources().openRawResource(R.raw.access_points);
        DetailItem details = null;
        try {
            details = detailItemReader.read( id , inputStream);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(details.Photo_1);

    }

}
