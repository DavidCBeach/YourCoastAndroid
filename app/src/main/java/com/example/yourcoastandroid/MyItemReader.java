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

package com.example.yourcoastandroid;

import android.location.Location;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MyItemReader {

    /*
     * This matches only once in whole input,
     * so Scanner.next returns whole InputStream as a String.
     * http://stackoverflow.com/a/5445161/2183804
     */
    public MyItemReader(Location location){
        Log.d("readerlocationfound", location.toString());
        userLat = location.getLatitude();
        userLon = location.getLongitude();
    }

    private MapsActivity mapsActivity = new MapsActivity();

    Double userLat;
    Double userLon;
    Double distance = 0.0;
//    public Location latlon(){
//       // Location userLoc = mapsActivity.getUserLocation();
//        userLat = userLoc.getLatitude();
//        userLon = userLoc.getLongitude();
//        return userLoc;
//    }
    private static final String REGEX_INPUT_BOUNDARY_BEGINNING = "\\A";
    private static DecimalFormat df2 = new DecimalFormat("#.#");

    public List<MyItem> read(InputStream inputStream) throws JSONException {

        List<MyItem> items = new ArrayList<MyItem>();
        String json = new Scanner(inputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            String title = null;
            //String ssnippet = null;

            String name = null;
            String description = null;

            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("LATITUDE");
            double lng = object.getDouble("LONGITUDE");
            distance = getDistance(userLat, userLon, lat, lng);

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
            Log.d("distance", String.valueOf(id) + " " + df2.format(distance));
            Double dis = round(distance, 1);
            items.add(new MyItem(lat, lng, title, ssnippet, id, name, description, dis));
        }
        return items;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

        public static double distance(double lat1,
                                  double lat2, double lon1,
                                  double lon2)
    {
        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);
        double c = 2 * Math.asin(Math.sqrt(a));
        // Radius of earth in miles
        double r = 3956;
        // calculate the result
        return((c * r));
    }

    double earthRadius = 3956;// Approximate radius of the earth in kilometers
    public double getDistance(double lat1, double long1, double lat2, double long2) {
        //Log.d("getdistance", lat1 + " " + long1 + " " + lat2 + " " + long2);
        double distance = Math.acos(Math.sin(lat2 * Math.PI / 180.0) * Math.sin(lat1 * Math.PI / 180.0) +
                Math.cos(lat2 * Math.PI / 180.0) * Math.cos(lat1 * Math.PI / 180.0) *
                        Math.cos((long1 - long2) * Math.PI / 180.0)) * earthRadius;
        return distance;
    }

}
