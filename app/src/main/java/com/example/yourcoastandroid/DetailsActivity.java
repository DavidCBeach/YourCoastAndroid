package com.example.yourcoastandroid;

import android.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;

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
        centerTitle();
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        System.out.println(details.RESTROOMS);
        TextView textViewToChange = (TextView) findViewById(R.id.address);
        textViewToChange.setText(details.LocationMobileWeb.toString());
        textViewToChange = (TextView) findViewById(R.id.phone);
        textViewToChange.setText(details.PHONE_NMBR.toString());
        setTitle(details.NameMobileWeb.toString());


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

}
