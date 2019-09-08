package com.yourcoast.yourcoastandroid;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.provider.BaseColumns;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DatabaseUtil {

    private String locations_url = "https://api.coastal.ca.gov/access/v1/locations";
    private CCCDataClient adapter;
    private RecyclerView recyclerView;
    Double userLat;
    Double userLon;

    public DatabaseUtil(Location location){
        userLat = location.getLatitude();
        userLon = location.getLongitude();
    }
    public DatabaseUtil(){
        userLat = null;
        userLon = null;
    }

    private void Write(JsonElement content, Activity activity) {
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(activity);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        JsonObject jo = content.getAsJsonObject();

        values.put(BaseColumns._ID , jo.get("ID").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DISTRICT , jo.get("DISTRICT").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CountyNum , jo.get("CountyNum").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_COUNTY , jo.get("COUNTY").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DescriptionMobileWeb , jo.get("DescriptionMobileWeb").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NameMobileWeb , jo.get("NameMobileWeb").toString());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LocationMobileWeb , jo.get("LocationMobileWeb").toString());
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

    public void getLocation(String response, Activity activity){


        Gson g = new Gson();
        JsonArray ja = g.fromJson(response, JsonArray.class);
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(activity);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from "+ FeedReaderContract.FeedEntry.TABLE_NAME);
        db.close();
        for(JsonElement r : ja){
            Write(r, activity);
        }

//                        //response is json object, parse using GSON
//                        GsonBuilder builder = new GsonBuilder();
//                        Gson gson = builder.create();
//                        //original line with error
//                        List<CCCAccPtDataStructure> list = Arrays.asList(gson.fromJson(response, CCCAccPtDataStructure[].class));
//                        //ArrayList<CCCAccPtDataStructure> list = (ArrayList<CCCAccPtDataStructure>) Arrays.asList(gson.fromJson(response, CCCAccPtDataStructure[].class));
//                        adapter = new CCCDataClient(list);
//                        recyclerView.setAdapter(adapter);
    }




    public List<MyItem> Read(Activity activity){
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
        while(cursor.moveToNext()) {
            String title = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NameMobileWeb));
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NameMobileWeb));
            String description = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DescriptionMobileWeb));
            Integer id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
            double lat = cursor.getFloat(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_LATITUDE));
            double lng = cursor.getFloat(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_LONGITUDE));

            String ssnippet = Integer.toString(id);
            title = title.substring(1,title.length()-1);
            name = name.substring(1,name.length()-1);
            description = description.substring(1,description.length()-1);
            distance = getDistance(userLat, userLon, lat, lng);
            Double dis = round(distance, 1);
            items.add(new MyItem(lat, lng, title, ssnippet, id, name, description, dis));

        }
        cursor.close();
        db.close();
        return items;
    }

    public DetailItem ReadbyID(Activity activity, String Id){
        Log.d("snake",Id);
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(activity);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String District;
        int CountyNum;
        String COUNTY;
        String NameMobileWeb;
        Double LATITUDE, LONGITUDE;
        String LocationMobileWeb, DescriptionMobileWeb, PHONE_NMBR, LIST_ORDER, GEOGR_AREA,
                Photo_1,Photo_2,Photo_3,Photo_4, Bch_whlchr, BIKE_PATH, BT_FACIL_TYPE;
        boolean FEE, PARKING, DSABLDACSS, RESTROOMS, VISTOR_CTR, DOG_FRIENDLY, EZ4STROLLERS, PCNC_AREA, CAMPGROUND,
                SNDY_BEACH, DUNES, RKY_SHORE, BLUFF, STRS_BEACH, PTH_BEACH,BLFTP_TRLS, BLFTP_PRK,WLDLFE_VWG,
                TIDEPOOL, VOLLEYBALL, FISHING, BOATING;

        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DISTRICT ,
                FeedReaderContract.FeedEntry.COLUMN_NAME_CountyNum ,
                FeedReaderContract.FeedEntry.COLUMN_NAME_COUNTY ,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DescriptionMobileWeb ,
                FeedReaderContract.FeedEntry.COLUMN_NAME_NameMobileWeb ,
                FeedReaderContract.FeedEntry.COLUMN_NAME_LocationMobileWeb ,
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

        String sortOrder = "";
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = {Id};

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        DetailItem item = new DetailItem();
        while(cursor.moveToNext()) {
            String title = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NameMobileWeb));
            Integer id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
            double lat = cursor.getFloat(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_LATITUDE));
            double lng = cursor.getFloat(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_LONGITUDE));

            String ssnippet = Integer.toString(id);
            title = title.substring(1,title.length()-1);







            Integer snippet = id;
            ssnippet =  id.toString();
            District = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DISTRICT));
            COUNTY = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_COUNTY));
            NameMobileWeb = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NameMobileWeb));
            LocationMobileWeb = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_LocationMobileWeb));
            DescriptionMobileWeb = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DescriptionMobileWeb));
            PHONE_NMBR = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_PHONE_NMBR));
            LIST_ORDER = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_LIST_ORDER));
            GEOGR_AREA = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_GEOGR_AREA));
            LATITUDE = lat;
            LONGITUDE = lng;
            Photo_1 = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Photo_1));
            Photo_2 = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Photo_2));
            Photo_3 = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Photo_3));
            Photo_4 = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Photo_4));
            Bch_whlchr = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Bch_whlchr));
            BIKE_PATH = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_BIKE_PATH));
            BT_FACIL_TYPE = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_BT_FACIL_TYPE));
            District = District.substring(1,District.length()-1);
            COUNTY = COUNTY.substring(1,COUNTY.length()-1);
            NameMobileWeb = NameMobileWeb.substring(1,NameMobileWeb.length()-1);
            LocationMobileWeb = LocationMobileWeb.substring(1,LocationMobileWeb.length()-1);
            DescriptionMobileWeb = DescriptionMobileWeb.substring(1,DescriptionMobileWeb.length()-1);
            PHONE_NMBR = PHONE_NMBR.substring(1,PHONE_NMBR.length()-1);
            LIST_ORDER = LIST_ORDER.substring(1,LIST_ORDER.length()-1);
            GEOGR_AREA = GEOGR_AREA.substring(1,GEOGR_AREA.length()-1);
            Photo_1 = Photo_1.substring(1,Photo_1.length()-1);
            Photo_2 = Photo_2.substring(1,Photo_2.length()-1);
            Photo_3 = Photo_3.substring(1,Photo_3.length()-1);
            Photo_4 = Photo_4.substring(1,Photo_4.length()-1);
            Bch_whlchr = Bch_whlchr.substring(1,Bch_whlchr.length()-1);
            BIKE_PATH = BIKE_PATH.substring(1,BIKE_PATH.length()-1);
            BT_FACIL_TYPE = BT_FACIL_TYPE.substring(1,BT_FACIL_TYPE.length()-1);




            Log.d("snake",LocationMobileWeb);




            String temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_FEE));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                FEE = true;
            } else {
                FEE = false;
            }
            temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_PARKING));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                PARKING = true;
            } else {
                PARKING = false;
            }
            temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DSABLDACSS));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                DSABLDACSS = true;
            } else {
                DSABLDACSS = false;
            }
            temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_RESTROOMS));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                RESTROOMS = true;
            } else {
                RESTROOMS = false;
            }
            temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_VISTOR_CTR));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                VISTOR_CTR = true;
            } else {
                VISTOR_CTR = false;
            }
            temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DOG_FRIENDLY));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                DOG_FRIENDLY = true;
            } else {
                DOG_FRIENDLY = false;
            }
            temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_EZ4STROLLERS));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                EZ4STROLLERS = true;
            } else {
                EZ4STROLLERS = false;
            }
            temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_PCNC_AREA));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                PCNC_AREA = true;
            } else {
                PCNC_AREA = false;
            }
            temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_CAMPGROUND));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                CAMPGROUND = true;
            } else {
                CAMPGROUND = false;
            }
            temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_SNDY_BEACH));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                SNDY_BEACH = true;
            } else {
                SNDY_BEACH = false;
            } temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DUNES));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                DUNES = true;
            } else {
                DUNES = false;
            } temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_RKY_SHORE));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                RKY_SHORE = true;
            } else {
                RKY_SHORE = false;
            }
            temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_BLUFF));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                BLUFF = true;
            } else {
                BLUFF = false;
            }
            temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_STRS_BEACH));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                STRS_BEACH = true;
            } else {
                STRS_BEACH = false;
            } temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_PTH_BEACH));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                PTH_BEACH = true;
            } else {
                PTH_BEACH = false;
            } temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_BLFTP_TRLS));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                BLFTP_TRLS = true;
            } else {
                BLFTP_TRLS = false;
            }temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_BLFTP_PRK));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                BLFTP_PRK = true;
            } else {
                BLFTP_PRK = false;
            } temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_WLDLFE_VWG));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                WLDLFE_VWG = true;
            } else {
                WLDLFE_VWG = false;
            }
            temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TIDEPOOL));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                TIDEPOOL = true;
            } else {
                TIDEPOOL = false;
            }
            temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_VOLLEYBALL));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                VOLLEYBALL = true;
            } else {
                VOLLEYBALL = false;
            } temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_FISHING));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                FISHING = true;
            } else {
                FISHING = false;
            } temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_BOATING));
            temp = temp.substring(1,temp.length()-1);
            if(temp.equals("Yes")){
                BOATING = true;
            } else {
                BOATING = false;
            }
            Log.d("value",title);
            item = new DetailItem(title, ssnippet, id, District,
                    -1,
                    COUNTY,
                    NameMobileWeb,
                    LocationMobileWeb, DescriptionMobileWeb, PHONE_NMBR, LIST_ORDER, GEOGR_AREA, LATITUDE, LONGITUDE,
                    Photo_1,Photo_2,Photo_3,Photo_4, Bch_whlchr, BIKE_PATH, BT_FACIL_TYPE,
                    FEE, PARKING, DSABLDACSS, RESTROOMS, VISTOR_CTR, DOG_FRIENDLY, EZ4STROLLERS, PCNC_AREA, CAMPGROUND,
                    SNDY_BEACH, DUNES, RKY_SHORE, BLUFF, STRS_BEACH, PTH_BEACH,BLFTP_TRLS, BLFTP_PRK,WLDLFE_VWG,
                    TIDEPOOL, VOLLEYBALL, FISHING, BOATING);
            Log.d("brgotr", String.valueOf(item));
        }


        cursor.close();
        db.close();
        Log.d("brgotr", String.valueOf(item));
        return item;
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
