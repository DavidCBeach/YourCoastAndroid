package com.yourcoast.yourcoastandroid;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yourcoast.yourcoastandroid.AccessPointData.CCCAccPtDataStructure;
import com.yourcoast.yourcoastandroid.AccessPointData.CCCDataClient;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DatabaseUtil {
    private FeedReaderDbHelper dbHelper;
    private String locations_url = "https://api.coastal.ca.gov/access/v1/locations";
    private CCCDataClient adapter;
    private RecyclerView recyclerView;


    public void UpdateData(){



    }

    private void Write(JsonElement content) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat mdformat = new SimpleDateFormat("MM/dd/yyyy");

        JsonObject jo = content.getAsJsonObject();

        values.put(FeedReaderContract.FeedEntry._ID , jo.get("ID").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DISTRICT , jo.get("DISTRICT").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CountyNum , jo.get("CountyNum").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_COUNTY , jo.get("COUNTY").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DescriptionMobileWeb , jo.get("DescriptionMobileWeb").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PHONE_NMBR , jo.get("PHONE_NMBR").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_FEE , jo.get("FEE").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PARKING , jo.get("PARKING").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DSABLDACSS , jo.get("DSABLDACSS").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RESTROOMS , jo.get("RESTROOMS").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_VISTOR_CTR , jo.get("VISTOR_CTR").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DOG_FRIENDLY , jo.get("DOG_FRIENDLY").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_EZ4STROLLERS , jo.get("EZ4STROLLERS").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PCNC_AREA , jo.get("PCNC_AREA ").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CAMPGROUND , jo.get("CAMPGROUND").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SNDY_BEACH , jo.get("SNDY_BEACH").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DUNES , jo.get("DUNES").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RKY_SHORE , jo.get("RKY_SHORE").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_BLUFF , jo.get("BLUFF").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_STRS_BEACH , jo.get("STRS_BEACH").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PTH_BEACH, jo.get("PTH_BEACH").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_BLFTP_TRLS , jo.get("BLFTP_TRLS").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_BLFTP_PRK , jo.get("BLFTP_PRK").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_WLDLFE_VWG , jo.get("WLDLFE_VWG").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TIDEPOOL , jo.get("TIDEPOOL").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_VOLLEYBALL , jo.get("VOLLEYBALL").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_FISHING , jo.get("FISHING").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_BOATING , jo.get("BOATING").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LIST_ORDER , jo.get("LIST_ORDER").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_GEOGR_AREA , jo.get("GEOGR_AREA").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LATITUDE , jo.get("LATITUDE").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LONGITUDE , jo.get("LONGITUDE").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Photo_1 , jo.get("Photo_1").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Photo_2 , jo.get("Photo_2").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Photo_3 , jo.get("Photo_3").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Photo_4 , jo.get("Photo_4").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Bch_whlchr , jo.get("Bch_whlchr").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_BIKE_PATH , jo.get("BIKE_PATH").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_BT_FACIL_TYPE , jo.get("BT_FACIL_TYPE").toString());




        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        System.out.println(newRowId);
        db.close();
    }

    public void getLocation(Activity activity){
        //volley string request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, locations_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson g = new Gson();
                        JsonArray ja = g.fromJson(response, JsonArray.class);
                        for(JsonElement r : ja){
                            Write(r);
                        }

                        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//                        //response is json object, parse using GSON
//                        GsonBuilder builder = new GsonBuilder();
//                        Gson gson = builder.create();
//                        //original line with error
//                        List<CCCAccPtDataStructure> list = Arrays.asList(gson.fromJson(response, CCCAccPtDataStructure[].class));
//                        //ArrayList<CCCAccPtDataStructure> list = (ArrayList<CCCAccPtDataStructure>) Arrays.asList(gson.fromJson(response, CCCAccPtDataStructure[].class));
//                        adapter = new CCCDataClient(list);
//                        recyclerView.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        VolleySingleton.getInstance(activity).addToRequestQueue(stringRequest);
    }
}
