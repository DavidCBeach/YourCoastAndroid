package com.example.yourcoastandroid;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Switch;

public class FilterActivity extends AppCompatActivity {
    Switch feeSwitch;
    ImageView moneyImage;

    Switch parkingSwitch;
    ImageView parkingImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        viewById();
        populateImageViews();
    }

    @SuppressLint("ResourceType")
    public void populateImageViews () {
        final TypedArray iconArray = getResources().obtainTypedArray(R.array.icons);

        moneyImage.setImageResource(iconArray.getResourceId(0,-1));
        parkingImage.setImageResource(iconArray.getResourceId(1,-1));

    }

    public void viewById() {
        moneyImage = findViewById(R.id.Money);
        feeSwitch = findViewById(R.id.feeSwitch);
        feeSwitch.setChecked(true);

        parkingImage = findViewById(R.id.Parking);
        parkingSwitch = findViewById(R.id.parkingSwitch);
        parkingSwitch.setChecked(true);
    }
}
