package com.example.yourcoastandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String id= getIntent().getStringExtra("DATA_ID");
        //this.getActionBar().setTitle(id);

    }

}
