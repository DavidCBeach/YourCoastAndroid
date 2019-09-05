package com.yourcoast.yourcoastandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_DISTRICT + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_CountyNum + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_COUNTY + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_DescriptionMobileWeb + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_NameMobileWeb + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_LocationMobileWeb + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_PHONE_NMBR + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_FEE + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_PARKING + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_DSABLDACSS + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_RESTROOMS + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_VISTOR_CTR + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_DOG_FRIENDLY + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_EZ4STROLLERS + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_PCNC_AREA + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_CAMPGROUND + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_SNDY_BEACH + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_DUNES + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_RKY_SHORE + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_BLUFF + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_STRS_BEACH + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_PTH_BEACH + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_BLFTP_TRLS + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_BLFTP_PRK + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_WLDLFE_VWG + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_TIDEPOOL + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_VOLLEYBALL + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_FISHING + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_BOATING + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_LIST_ORDER + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_GEOGR_AREA + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_LATITUDE + " DECIMAL," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_LONGITUDE + " DECIMAL," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_Photo_1 + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_Photo_2 + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_Photo_3 + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_Photo_4 + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_Bch_whlchr + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_BIKE_PATH + " TEXT," +
    FeedReaderContract.FeedEntry.COLUMN_NAME_BT_FACIL_TYPE + " TEXT)" ;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 11;
    public static final String DATABASE_NAME = "FeedReader.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}
