/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yourcoast.yourcoastandroid;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MyItemReader implements Serializable {

    /*
     * This matches only once in whole input,
     * so Scanner.next returns whole InputStream as a String.
     * http://stackoverflow.com/a/5445161/2183804
     */
    public MyItemReader(Location location){
        userLat = location.getLatitude();
        userLon = location.getLongitude();
    }

    private MapsActivity mapsActivity = new MapsActivity();

    Double userLat;
    Double userLon;
    Double distance = 0.0;

    private static final String REGEX_INPUT_BOUNDARY_BEGINNING = "\\A";

    public List<MyItem> readByID(InputStream inputStream, ArrayList<Integer >ID) throws JSONException {
        String json = new Scanner(inputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next();
        JSONArray array = new JSONArray(json);
        List<MyItem> items = new ArrayList<MyItem>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            if (ID.contains(object.getInt("ID"))){
                String title = null;
                String name = null;
                String description = null;

                double lat = object.getDouble("LATITUDE");
                double lng = object.getDouble("LONGITUDE");

                int id = object.getInt("ID");
                Integer snippet = object.getInt("ID");
                String ssnippet = snippet.toString();
                if (!object.isNull("NameMobileWeb")) {
                    title = object.getString("NameMobileWeb");
                    name = object.getString("NameMobileWeb");
                }

                if (!object.isNull("DescriptionMobileWeb")) {
                    description = object.getString("DescriptionMobileWeb");
                }
                distance = getDistance(userLat, userLon, lat, lng);
                Double dis = round(distance, 1);

                items.add(new MyItem(lat, lng, title, ssnippet, id, name, description, dis));
            }
        }
        return items;
    }

//    public List<MyItem> read(InputStream inputStream) throws JSONException {
//
//        List<MyItem> items = new ArrayList<MyItem>();
//        Scanner myScanner = new Scanner(inputStream);
//        String json = new String();
//        json =myScanner.useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next();
//
//
//        JSONArray array = new JSONArray(json);
//        for (int i = 0; i < array.length(); i++) {
//            String title = null;
//            String name = null;
//            String description = null;
//
//            JSONObject object = array.getJSONObject(i);
//            //double lat =;
//            //double lng = ;
//
//            int id = ; ;
//            //String ssnippet = id;
//            if (!object.isNull("NameMobileWeb")) {
//                title = ;
//                name = ;
//            }
//
//            if (!object.isNull("DescriptionMobileWeb")) {
//                description = ;
//            }
//            //distance = getDistance(userLat, userLon, lat, lng);
//            Double dis = round(distance, 1);
//            ///.add(new MyItem(lat, lng, title, ssnippet, id, name, description, dis));
//        }
//        return items;
//    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    double earthRadius = 3956;// Approximate radius of the earth in miles
    public double getDistance(double lat1, double long1, double lat2, double long2) {
        double distance = Math.acos(Math.sin(lat2 * Math.PI / 180.0) * Math.sin(lat1 * Math.PI / 180.0) +
                Math.cos(lat2 * Math.PI / 180.0) * Math.cos(lat1 * Math.PI / 180.0) *
                        Math.cos((long1 - long2) * Math.PI / 180.0)) * earthRadius;
        return distance;
    }
}
