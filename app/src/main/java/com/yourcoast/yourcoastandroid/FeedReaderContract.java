package com.yourcoast.yourcoastandroid;


import android.provider.BaseColumns;

public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.

    private FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_DATE_MILLI = "milli";
    }






}
