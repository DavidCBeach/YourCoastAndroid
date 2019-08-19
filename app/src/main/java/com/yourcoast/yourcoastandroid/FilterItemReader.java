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
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.math.RoundingMode;

public class FilterItemReader {
    /*
     * This matches only once in whole input,
     * so Scanner.next returns whole InputStream as a String.
     * http://stackoverflow.com/a/5445161/2183804
     */
    public FilterItemReader(Location location){
        Log.d("readerlocationfound", location.toString());
        userLat = location.getLatitude();
        userLon = location.getLongitude();
    }

    private MapsActivity mapsActivity = new MapsActivity();

    Double userLat;
    Double userLon;
    Double distance = 0.0;

    private static final String REGEX_INPUT_BOUNDARY_BEGINNING = "\\A";

    public List<FilterItem> read(InputStream inputStream) throws JSONException {

        List<FilterItem> items = new ArrayList<>();
        String json = new Scanner(inputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            String title = null;
            String name = null;
            String description = null;
            String fee = null;
            String parking = null;
            String disabled = null;
            String bluff = null;
            String tidepooles = null;
            String bike = null;
            String visitor = null;
            String restrooms = null;
            String picnic = null;
            String pet = null;
            String campground = null;
            String stroller = null;
            String volleyball = null;
            String sandy = null;
            String rocky = null;
            String stair = null;
            String path = null;
            String bluffTrail = null;
            String bluffPark = null;
            String dunes = null;
            String fishing = null;
            String wildlife = null;
            String boating = null;







            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("LATITUDE");
            double lng = object.getDouble("LONGITUDE");

            int id = object.getInt("ID");
            Integer snippet = object.getInt("ID");
            String ssnippet = snippet.toString();
            if (!object.isNull("NameMobileWeb")) {
                title = object.getString("NameMobileWeb");
                name = object.getString("NameMobileWeb");
            }

            if (!object.isNull("DescriptionMobileWeb")) { description = object.getString("DescriptionMobileWeb"); }
            if(!object.isNull("FEE")){ fee = object.getString("FEE"); }
            if(!object.isNull("PARKING")){ parking = object.getString("PARKING"); }
            if(!object.isNull("DSABLDACSS")){ disabled = object.getString("DSABLDACSS"); }
            if(!object.isNull("BLUFF")){ bluff = object.getString("BLUFF"); }
            if(!object.isNull("TIDEPOOL")){ tidepooles = object.getString("TIDEPOOL"); }
            if(!object.isNull("BIKE_PATH")){ bike = object.getString("BIKE_PATH"); }
            if(!object.isNull("VISTOR_CTR")){ visitor = object.getString("VISTOR_CTR"); }
            if(!object.isNull("RESTROOMS")){ restrooms = object.getString("RESTROOMS"); }
            if(!object.isNull("PCNC_AREA")){ picnic = object.getString("PCNC_AREA"); }
            if(!object.isNull("DOG_FRIENDLY")){ pet = object.getString("DOG_FRIENDLY"); }
            if(!object.isNull("CAMPGROUND")){ campground = object.getString("CAMPGROUND"); }
            if(!object.isNull("EZ4STROLLERS")){ stroller = object.getString("EZ4STROLLERS"); }
            if(!object.isNull("VOLLEYBALL")){ volleyball = object.getString("VOLLEYBALL"); }
            if(!object.isNull("SNDY_BEACH")){ sandy = object.getString("SNDY_BEACH"); }
            if(!object.isNull("RKY_SHORE")){ rocky = object.getString("RKY_SHORE"); }
            if(!object.isNull("STRS_BEACH")){ stair = object.getString("STRS_BEACH"); }
            if(!object.isNull("PTH_BEACH")){ path = object.getString("PTH_BEACH"); }
            if(!object.isNull("BLFTP_TRLS")){ bluffTrail = object.getString("BLFTP_TRLS"); }
            if(!object.isNull("BLFTP_PRK")){ bluffPark = object.getString("BLFTP_PRK"); }
            if(!object.isNull("DUNES")){ dunes = object.getString("DUNES"); }
            if(!object.isNull("FISHING")){ fishing = object.getString("FISHING"); }
            if(!object.isNull("WLDLFE_VWG")){ wildlife = object.getString("WLDLFE_VWG"); }
            if(!object.isNull("BOATING")){ boating = object.getString("BOATING"); }



            distance = getDistance(userLat, userLon, lat, lng);
            Double dis = round(distance, 1);
            items.add(new FilterItem(lat, lng, title, ssnippet, id, name, description, dis,fee,parking,disabled,bluff,tidepooles,bike,visitor,restrooms,picnic,pet,campground,stroller,volleyball,sandy,rocky,stair,path,bluffTrail,bluffPark,dunes,fishing,wildlife,boating));
        }
        return items;
    }

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
