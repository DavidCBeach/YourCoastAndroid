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

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class FilterItem implements ClusterItem, Comparable<FilterItem>{
    private final LatLng mPosition;
    private String mTitle;
    private String mSnippet;
    private int mID;

    private String lNameMobileWeb;
    private String lDescriptionMobileWeb;
    private int lID;
    private Double lDistance;
    private String mFee;
    private String mParking;
    private String disabled;
    private String bluff;
    private String tidepooles;
    private String bike;
    private String visitor;
    private String restrooms;
    private String picnic;
    private String pet;
    private String campground;
    private String stroller;
    private String volleyball;
    private String sandy;
    private String rocky;
    private String stair;
    private String path;
    private String bluffTrail;
    private String bluffPark;
    private String dunes;
    private String fishing;
    private String wildlife;
    private String boating;



    public FilterItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
        mTitle = null;
        mSnippet = null;
        mID = 0;

        lNameMobileWeb = null;
        lDescriptionMobileWeb = null;
        lID = 0;
        lDistance = 0.0;
    }

    public FilterItem(double lat, double lng, String title, String snippet, int id, String name, String description, Double distance, String fee, String parking,String disabled,String bluff,String tidepooles,String bike,String visitor,String restrooms,String picnic,String pet,String campground,String stroller,String volleyball, String sandy, String rocky, String stair, String path, String bluffTrail,String bluffPark, String dunes, String fishing, String wildlife, String boating) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
        mID = id;

        lNameMobileWeb = name;
        lDescriptionMobileWeb = description;
        lID = id;
        lDistance = distance;
        mFee = fee;
        mParking = parking;
        this.disabled = disabled;
        this.bluff = bluff;
        this.tidepooles = tidepooles;
        this.bike = bike;
        this.visitor = visitor;
        this.restrooms = restrooms;
        this.picnic = picnic;
        this.pet = pet;
        this.campground = campground;
        this.stroller = stroller;
        this.volleyball = volleyball;
        this.sandy = sandy;
        this.rocky = rocky;
        this.stair = stair;
        this.path = path;
        this.bluffTrail = bluffTrail;
        this.bluffPark = bluffPark;
        this.dunes = dunes;
        this.fishing = fishing;
        this.wildlife = wildlife;
        this.boating = boating;



    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() { return mTitle; }

    @Override
    public String getSnippet() { return mSnippet; }

    public int getID() { return mID; }

    public String getName() { return lNameMobileWeb; }

    public String getDescription() { return lDescriptionMobileWeb; }

    public Double getDistance(){return lDistance;}

    public String getFee(){return mFee;}

    public String getParking() {return mParking;}
    public String getDisabled(){return disabled;}

    public String getBluff() {return bluff;}
    public String getTidepooles(){return tidepooles;}

    public String getBike() {return bike;}
    public String getVisitor(){return visitor;}

    public String getRestrooms() {return restrooms;}
    public String getPicnic(){return picnic;}

    public String getPet() {return pet;}
    public String getCampground(){return campground;}

    public String getStroller() {return stroller;}
    public String getVolleyball(){return volleyball;}

    public String getSandy(){return sandy;}
    public String  getRocky() {return rocky;}
    public String  getStair() {return stair;}
    public String  getPath() {return path;}
    public String  getBluffTrail() {return bluffTrail;}
    public String  getBluffPark() {return bluffPark;}
    public String  getDunes() {return dunes;}
    public String  getFishing() {return fishing;}
    public String  getWildlife() {return wildlife;}
    public String  getBoating() {return boating;}







    /**
     * Set the title of the marker
     * @param title string to be set as title
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * Set the description of the marker
     * @param snippet string to be set as snippet
     */
    public void setSnippet(String snippet) {
        mSnippet = snippet;
    }

    @Override
    public int compareTo(FilterItem item) {
        if (getDistance() == null || item.getDistance() == null) {
            return 0;
        }
        return getDistance().compareTo(item.getDistance());
    }
}
