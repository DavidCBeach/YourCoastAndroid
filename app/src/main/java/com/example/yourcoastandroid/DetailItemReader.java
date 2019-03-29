package com.example.yourcoastandroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DetailItemReader {
    private static final String REGEX_INPUT_BOUNDARY_BEGINNING = "\\A";

    public DetailItem read(String ID,InputStream inputStream) throws JSONException {
        DetailItem item = new DetailItem();
        String json = new Scanner(inputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next();
        JSONArray array = new JSONArray(json);
        String District;
        int CountyNum;
        String COUNTY;
        String NameMobileWeb;
        String LocationMobileWeb, DescriptionMobileWeb, PHONE_NMBR, LIST_ORDER, GEOGR_AREA, LATITUDE, LONGITUDE,
                Photo_1,Photo_2,Photo_3,Photo_4, Bch_whlchr, BIKE_PATH, BT_FACIL_TYPE;
        boolean FEE, PARKING, DSABLDACSS, RESTROOMS, VISTOR_CTR, DOG_FRIENDLY, EZ4STROLLERS, PCNC_AREA, CAMPGROUND,
                SNDY_BEACH, DUNES, RKY_SHORE, BLUFF, STRS_BEACH, PTH_BEACH,BLFTP_TRLS, BLFTP_PRK,WLDLFE_VWG,
                TIDEPOOL, VOLLEYBALL, FISHING, BOATING;
        for (int i = 0; i < array.length(); i++) {
            String title = null;
            //String ssnippet = null;
            JSONObject object = array.getJSONObject(i);
            int id = object.getInt("ID");
            //System.out.println(Integer.parseInt(ID));
            //System.out.println(id);
            if(id == Integer.parseInt(ID)){
                CountyNum= object.getInt("CountyNum");
                Integer snippet = object.getInt("ID");
                String ssnippet = snippet.toString();
                if (!object.isNull("NameMobileWeb")) {
                    title = object.getString("NameMobileWeb");
                }
                District = object.getString("DISTRICT");
                COUNTY = object.getString("COUNTY");
                NameMobileWeb = object.getString("NameMobileWeb");
                LocationMobileWeb = object.getString("LocationMobileWeb");
                DescriptionMobileWeb = object.getString("DescriptionMobileWeb");
                PHONE_NMBR = object.getString("PHONE_NMBR");
                LIST_ORDER = object.getString("LIST_ORDER");
                GEOGR_AREA = object.getString("GEOGR_AREA");
                LATITUDE = object.getString("LATITUDE");
                LONGITUDE = object.getString("LONGITUDE");
                Photo_1 = object.getString("Photo_1");
                Photo_2 = object.getString("Photo_2");
                Photo_3 = object.getString("Photo_3");
                Photo_4 = object.getString("Photo_4");
                Bch_whlchr = object.getString("Bch_whlchr");
                BIKE_PATH = object.getString("BIKE_PATH");
                BT_FACIL_TYPE = object.getString("BT_FACIL_TYPE");
                String temp = object.getString("FEE");
                if(temp == "Yes"){
                    FEE = true;
                } else {
                    FEE = false;
                }
                 temp = object.getString("PARKING");
                if(temp == "Yes"){
                    PARKING = true;
                } else {
                    PARKING = false;
                }
                 temp = object.getString("DSABLDACSS");
                if(temp == "Yes"){
                    DSABLDACSS = true;
                } else {
                    DSABLDACSS = false;
                }
                 temp = object.getString("RESTROOMS");
                if(temp == "Yes"){
                    RESTROOMS = true;
                } else {
                    RESTROOMS = false;
                }
                 temp = object.getString("VISTOR_CTR");
                if(temp == "Yes"){
                    VISTOR_CTR = true;
                } else {
                    VISTOR_CTR = false;
                }
                 temp = object.getString("DOG_FRIENDLY");
                if(temp == "Yes"){
                    DOG_FRIENDLY = true;
                } else {
                    DOG_FRIENDLY = false;
                }
                 temp = object.getString("EZ4STROLLERS");
                if(temp == "Yes"){
                    EZ4STROLLERS = true;
                } else {
                    EZ4STROLLERS = false;
                }
                 temp = object.getString("PCNC_AREA");
                if(temp == "Yes"){
                    PCNC_AREA = true;
                } else {
                    PCNC_AREA = false;
                }
                 temp = object.getString("CAMPGROUND");
                if(temp == "Yes"){
                    CAMPGROUND = true;
                } else {
                    CAMPGROUND = false;
                }
                 temp = object.getString("SNDY_BEACH");
                if(temp == "Yes"){
                    SNDY_BEACH = true;
                } else {
                    SNDY_BEACH = false;
                } temp = object.getString("DUNES");
                if(temp == "Yes"){
                    DUNES = true;
                } else {
                    DUNES = false;
                } temp = object.getString("RKY_SHORE");
                if(temp == "Yes"){
                    RKY_SHORE = true;
                } else {
                    RKY_SHORE = false;
                }
                 temp = object.getString("BLUFF");
                if(temp == "Yes"){
                    BLUFF = true;
                } else {
                    BLUFF = false;
                }
                 temp = object.getString("STRS_BEACH");
                if(temp == "Yes"){
                    STRS_BEACH = true;
                } else {
                    STRS_BEACH = false;
                } temp = object.getString("PTH_BEACH");
                if(temp == "Yes"){
                    PTH_BEACH = true;
                } else {
                    PTH_BEACH = false;
                } temp = object.getString("BLFTP_TRLS");
                if(temp == "Yes"){
                    BLFTP_TRLS = true;
                } else {
                    BLFTP_TRLS = false;
                } temp = object.getString("BLFTP_PRK");
                if(temp == "Yes"){
                    BLFTP_PRK = true;
                } else {
                    BLFTP_PRK = false;
                } temp = object.getString("WLDLFE_VWG");
                if(temp == "Yes"){
                    WLDLFE_VWG = true;
                } else {
                    WLDLFE_VWG = false;
                }
                 temp = object.getString("TIDEPOOL");
                if(temp == "Yes"){
                    TIDEPOOL = true;
                } else {
                    TIDEPOOL = false;
                }
                 temp = object.getString("VOLLEYBALL");
                if(temp == "Yes"){
                    VOLLEYBALL = true;
                } else {
                    VOLLEYBALL = false;
                } temp = object.getString("FISHING");
                if(temp == "Yes"){
                    FISHING = true;
                } else {
                    FISHING = false;
                } temp = object.getString("BOATING");
                if(temp == "Yes"){
                    BOATING = true;
                } else {
                    BOATING = false;
                }

                item = new DetailItem(title, ssnippet, id, District,
                 CountyNum,
                 COUNTY,
                 NameMobileWeb,
                 LocationMobileWeb, DescriptionMobileWeb, PHONE_NMBR, LIST_ORDER, GEOGR_AREA, LATITUDE, LONGITUDE,
                        Photo_1,Photo_2,Photo_3,Photo_4, Bch_whlchr, BIKE_PATH, BT_FACIL_TYPE,
                 FEE, PARKING, DSABLDACSS, RESTROOMS, VISTOR_CTR, DOG_FRIENDLY, EZ4STROLLERS, PCNC_AREA, CAMPGROUND,
                        SNDY_BEACH, DUNES, RKY_SHORE, BLUFF, STRS_BEACH, PTH_BEACH,BLFTP_TRLS, BLFTP_PRK,WLDLFE_VWG,
                        TIDEPOOL, VOLLEYBALL, FISHING, BOATING);
            }
        }
        return item;
    }

}
