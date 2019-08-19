package com.yourcoast.yourcoastandroid;

import com.google.android.gms.maps.model.LatLng;

public class DetailItem {
    public String Title;
    public String Snippet;
    public int ID;
    public String District;
    public int CountyNum;
    public String COUNTY;
    public double LATITUDE, LONGITUDE;
    public String NameMobileWeb;
    public String LocationMobileWeb, DescriptionMobileWeb, PHONE_NMBR, LIST_ORDER, GEOGR_AREA,
            Photo_1,Photo_2,Photo_3,Photo_4, Bch_whlchr, BIKE_PATH, BT_FACIL_TYPE;
    public boolean FEE, PARKING, DSABLDACSS, RESTROOMS, VISTOR_CTR, DOG_FRIENDLY, EZ4STROLLERS, PCNC_AREA, CAMPGROUND,
            SNDY_BEACH, DUNES, RKY_SHORE, BLUFF, STRS_BEACH, PTH_BEACH,BLFTP_TRLS, BLFTP_PRK,WLDLFE_VWG,
            TIDEPOOL, VOLLEYBALL, FISHING, BOATING;



    public DetailItem() {

        Title = null;
        Snippet = null;
        ID = 0;
    }

    public DetailItem(String title, String snippet, int id, String District,
                      int CountyNum ,
                      String COUNTY ,
                      String NameMobileWeb ,
                      String LocationMobileWeb,
                      String DescriptionMobileWeb,
                      String PHONE_NMBR,
                      String LIST_ORDER,
                      String GEOGR_AREA,
                      double LATITUDE, double LONGITUDE,
                      String Photo_1,
                      String Photo_2,
                      String Photo_3,
                      String Photo_4,
                      String Bch_whlchr,
                      String BIKE_PATH,
                      String BT_FACIL_TYPE,
                      boolean FEE ,
                      boolean PARKING,
                      boolean DSABLDACSS,
                      boolean RESTROOMS,
                      boolean VISTOR_CTR,
                      boolean DOG_FRIENDLY,
                      boolean EZ4STROLLERS,
                      boolean PCNC_AREA,
                      boolean CAMPGROUND,
                      boolean SNDY_BEACH,
                      boolean DUNES,
                      boolean RKY_SHORE,
                      boolean BLUFF,
                      boolean STRS_BEACH,
                      boolean PTH_BEACH,
                      boolean  BLFTP_TRLS,
                      boolean BLFTP_PRK,
                      boolean WLDLFE_VWG,
                      boolean TIDEPOOL,
                      boolean VOLLEYBALL,
                      boolean FISHING,
                      boolean BOATING ) {
        Title = title;
        Snippet = snippet;
        ID = id;
        this.District = District;
        this.CountyNum = CountyNum;
        this.COUNTY = COUNTY;
        this.NameMobileWeb = NameMobileWeb ;
        this.LocationMobileWeb= LocationMobileWeb;
        this.DescriptionMobileWeb= DescriptionMobileWeb;
        this.PHONE_NMBR= PHONE_NMBR;
        this.LIST_ORDER= LIST_ORDER;
        this.GEOGR_AREA= GEOGR_AREA;
        this.LATITUDE=LATITUDE;
        this.LONGITUDE=LONGITUDE;
        this.Photo_1=Photo_1;
        this.Photo_2=Photo_2;
        this.Photo_3= Photo_3;
        this.Photo_4=Photo_4 ;
        this.Bch_whlchr= Bch_whlchr;
        this.BIKE_PATH= BIKE_PATH;
        this.BT_FACIL_TYPE= BT_FACIL_TYPE;
        this.FEE = FEE;
        this.PARKING=PARKING;
        this.DSABLDACSS=DSABLDACSS;
        this.RESTROOMS=RESTROOMS;
        this.VISTOR_CTR=VISTOR_CTR ;
        this.DOG_FRIENDLY= DOG_FRIENDLY;
        this.EZ4STROLLERS= EZ4STROLLERS;
        this.PCNC_AREA= PCNC_AREA;
        this.CAMPGROUND=CAMPGROUND ;
        this.SNDY_BEACH= SNDY_BEACH;
        this.DUNES= DUNES;
        this.RKY_SHORE=RKY_SHORE ;
        this.BLUFF=BLUFF ;
        this.STRS_BEACH= STRS_BEACH;
        this.PTH_BEACH= PTH_BEACH;
        this.BLFTP_TRLS= BLFTP_TRLS;
        this.BLFTP_PRK= BLFTP_PRK;
        this.WLDLFE_VWG= WLDLFE_VWG;
        this.TIDEPOOL= TIDEPOOL;
        this.VOLLEYBALL= VOLLEYBALL;
        this.FISHING=FISHING;
        this.BOATING = BOATING;
    }

}
