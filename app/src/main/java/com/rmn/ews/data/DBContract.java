package com.rmn.ews.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by rmn on 29-08-2016.
 */
public class DBContract {

    public static final String CONTENT_AUTHORITY = "com.rmn.ews";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_SOURCE="source";
    public static final String PATH_OFFLINE="offlineData";

    public static final class SourceTable implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SOURCE).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/"
                + CONTENT_AUTHORITY + "/" + PATH_SOURCE;

        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/"
                + CONTENT_AUTHORITY + "/" + PATH_SOURCE;

        public static final String TABLE_NAME = "SourceTable";
        public static final String COLUMN_ID = "srcId";
        public static final String COLUMN_SRC_NAME = "srcName";
        public static final String COLUMN_SRC_URL_PRAM = "srcUrlPram";

        public static Uri buildInsertedRowUri(long _id) {
            return ContentUris.withAppendedId(CONTENT_URI, _id);
        }

    }



    public static class OfflineData implements BaseColumns{

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_OFFLINE).build();
        public static final String TABLE_NAME="offlineDataTable";
        public static final String COLUMN_ID="aId";
        public static final String COLUMN_TITLE="title";
        public static final String COLUMN_DESCRIPTION="desc";
        public static final String COLUMN_SOURCE="source";
        public static final String COLUMN_IMAGE="image";
        public static final String COLUMN_IMAGE_URL="imageUrl";
    }
}
