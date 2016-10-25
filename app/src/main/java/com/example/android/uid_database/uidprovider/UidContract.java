package com.example.android.uid_database.uidprovider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;




public class UidContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.uid_database.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static String UID_PATH = "uid";

    public static Uri getContentUri(String PATH_PARAM) {
        return BASE_CONTENT_URI.buildUpon().appendPath(PATH_PARAM).build();
    }

    public static String getContentType(String PATH_PARAM) {
        return ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PARAM;
    }

    public static String getContentItemType(String PATH_PARAM) {
        return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PARAM;
    }

    public static class UidStore implements BaseColumns {
        public static final String TABLE_NAME = "UidTable";

        public static final Uri CONTENT_URI = getContentUri(UID_PATH);
        public static final String CONTENT_TYPE = getContentType(UID_PATH);
        public static final String CONTENT_ITEM_TYPE = getContentItemType(UID_PATH);

        public final static String COLUMN_UID = "uid";
        public final static String COLUMN_DATE = "date";

        public static Uri buildUidUri(){
            return CONTENT_URI.buildUpon().build();
        }

        public static Uri buildUidUriWithDate(String Date) {
            return CONTENT_URI.buildUpon().appendPath(Date).build();
        }

        public static String getDateFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }
}
