package com.yourcoast.yourcoastandroid;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DatabaseUtil {

    private String locations_url = "https://api.coastal.ca.gov/access/v1/locations";
    private CCCDataClient adapter;
    private RecyclerView recyclerView;
    Double userLat;
    Double userLon;


    public void UpdateData(){



    }

    private void Write(JsonElement content, Activity activity) {
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(activity);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        JsonObject jo = content.getAsJsonObject();


        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DISTRICT , jo.get("DISTRICT").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CountyNum , jo.get("CountyNum").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_COUNTY , jo.get("COUNTY").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DescriptionMobileWeb , jo.get("DescriptionMobileWeb").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NameMobileWeb , jo.get("NameMobileWeb").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PHONE_NMBR , jo.get("PHONE_NMBR").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_FEE , jo.get("FEE").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PARKING , jo.get("PARKING").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DSABLDACSS , jo.get("DSABLDACSS").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RESTROOMS , jo.get("RESTROOMS").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_VISTOR_CTR , jo.get("VISTOR_CTR").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DOG_FRIENDLY , jo.get("DOG_FRIENDLY").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_EZ4STROLLERS , jo.get("EZ4STROLLERS").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PCNC_AREA , jo.get("PCNC_AREA").toString());
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

    public void getLocation(final Activity activity){
        //volley string request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, locations_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson g = new Gson();
                        JsonArray ja = g.fromJson(response, JsonArray.class);
                        for(JsonElement r : ja){
                            Write(r, activity);
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


    private List<MyItem> Read(Activity activity){
        List<MyItem> items = new ArrayList<MyItem>();
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(activity);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_DISTRICT ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_CountyNum ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_COUNTY ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_DescriptionMobileWeb ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_NameMobileWeb ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_PHONE_NMBR ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_FEE ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_PARKING ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_DSABLDACSS ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_RESTROOMS ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_VISTOR_CTR ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_DOG_FRIENDLY ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_EZ4STROLLERS ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_PCNC_AREA ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_CAMPGROUND ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_SNDY_BEACH ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_DUNES ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_RKY_SHORE ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_BLUFF ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_STRS_BEACH ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_PTH_BEACH ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_BLFTP_TRLS ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_BLFTP_PRK ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_WLDLFE_VWG ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_TIDEPOOL ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_VOLLEYBALL ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_FISHING ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_BOATING ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_LIST_ORDER ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_GEOGR_AREA ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_LATITUDE ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_LONGITUDE ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_Photo_1 ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_Photo_2 ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_Photo_3 ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_Photo_4 ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_Bch_whlchr ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_BIKE_PATH ,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_BT_FACIL_TYPE
        };

        String selection = "";
        String[] selectionArgs = {};
        String sortOrder = "";


        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        Double distance = 0.0;
        String contents = new String();
        ArrayList<String> listCon = new ArrayList();
        while(cursor.moveToNext()) {
            String title = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NameMobileWeb));
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NameMobileWeb));
            String description = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DescriptionMobileWeb));
            Integer id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_ID));
            double lat = cursor.getFloat(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_LATITUDE));
            double lng = cursor.getFloat(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_LONGITUDE));

            String ssnippet = Integer.toString(id);

            distance = getDistance(userLat, userLon, lat, lng);
            Double dis = round(distance, 1);
            items.add(new MyItem(lat, lng, title, ssnippet, id, name, description, dis));

        }
        cursor.close();
        db.close();

        return items;

    }
    public MyItemReader(Location location){
        userLat = location.getLatitude();
        userLon = location.getLongitude();
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    double earthRadius = 3956;
    public double getDistance(double lat1, double long1, double lat2, double long2) {
        double distance = Math.acos(Math.sin(lat2 * Math.PI / 180.0) * Math.sin(lat1 * Math.PI / 180.0) +
                Math.cos(lat2 * Math.PI / 180.0) * Math.cos(lat1 * Math.PI / 180.0) *
                        Math.cos((long1 - long2) * Math.PI / 180.0)) * earthRadius;
        return distance;
    }
}
