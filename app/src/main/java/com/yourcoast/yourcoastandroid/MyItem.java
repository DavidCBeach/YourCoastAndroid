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

public class MyItem implements ClusterItem, Comparable<MyItem> {
    private final LatLng mPosition;
    private String mTitle;
    private String mSnippet;
    private int mID;

    private String lNameMobileWeb;
    private String lDescriptionMobileWeb;
    private int lID;
    private Double lDistance;


    public MyItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
        mTitle = null;
        mSnippet = null;
        mID = 0;

        lNameMobileWeb = null;
        lDescriptionMobileWeb = null;
        lID = 0;
        lDistance = 0.0;
    }

    public MyItem(double lat, double lng, String title, String snippet, int id, String name, String description, Double distance) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
        mID = id;

        lNameMobileWeb = name;
        lDescriptionMobileWeb = description;
        lID = id;
        lDistance = distance;
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
    public int compareTo(MyItem item) {
        if (getDistance() == null || item.getDistance() == null) {
            return 0;
        }
        return getDistance().compareTo(item.getDistance());
    }
}
