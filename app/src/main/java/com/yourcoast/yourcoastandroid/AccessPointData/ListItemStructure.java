package com.yourcoast.yourcoastandroid.AccessPointData;

public class ListItemStructure{
    private String lNameMobileWeb;
    private String lDescriptionMobileWeb;
    private int lID;


    public  ListItemStructure() {
        lNameMobileWeb = null;
        lDescriptionMobileWeb = null;
        lID = 0;
    }

    public  ListItemStructure(String name, String description, int id) {
        lNameMobileWeb = name;
        lDescriptionMobileWeb = description;
        lID = id;
    }


    public String getName() { return lNameMobileWeb; }

    public String getDescription() { return lDescriptionMobileWeb; }

    public int getID() { return lID; }


}
