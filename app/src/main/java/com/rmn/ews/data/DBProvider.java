package com.rmn.ews.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import static com.rmn.ews.data.DBContract.*;

import android.support.annotation.Nullable;

import com.rmn.ews.utiles.DBHelper;

/**
 * Created by rmn on 29-08-2016.
 */
public class DBProvider extends ContentProvider {


    public SQLiteDatabase dbHelper;
    public static UriMatcher uriMatcher = buildUriMatcher();
    public static final String CONTENT_AUTHORITY = "com.rmn.ews";
    public static final int SOURCE = 100;
    public static final int OFFLINE_DATA = 101;


    public static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = CONTENT_AUTHORITY;

        matcher.addURI(authority, PATH_SOURCE, SOURCE);
        matcher.addURI(authority, PATH_OFFLINE, OFFLINE_DATA);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String s1) {

        Cursor cursor = null;
        dbHelper = new DBHelper(getContext()).getReadableDatabase();

        int match = uriMatcher.match(uri);

        switch (match) {
            case SOURCE:
                cursor = dbHelper.query(SourceTable.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                break;
            case OFFLINE_DATA:
                cursor = dbHelper.query(OfflineData.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                break;
            default:
                throw new UnsupportedOperationException("query  UNKNOWN URI:------>" + uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        final int match = uriMatcher.match(uri);
        if (match == SOURCE) {
            return SourceTable.CONTENT_ITEM_TYPE;
        } else
            return null;
    }
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        dbHelper = new DBHelper(getContext()).getReadableDatabase();
        long _id;
        Uri returnUri;
        final int match = uriMatcher.match(uri);
        switch (match) {

            case SOURCE:
                _id = dbHelper.insert(SourceTable.TABLE_NAME, null, contentValues);
                break;
            case OFFLINE_DATA:
                _id = dbHelper.insert(OfflineData.TABLE_NAME, null, contentValues);
                break;
            default:
                throw new UnsupportedOperationException("insert unkown uri " + uri);
        }
        if (_id > 0) {
            returnUri = SourceTable.buildInsertedRowUri(_id);
        } else throw new android.database.SQLException("failed to insert in " + uri);

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final int match = uriMatcher.match(uri);
        int rowDeleted;
        dbHelper = new DBHelper(getContext()).getReadableDatabase();

        switch (match) {

            case SOURCE:
                rowDeleted = dbHelper.delete(SourceTable.TABLE_NAME, selection, selectionArgs);
                break;

            case OFFLINE_DATA:
                rowDeleted = dbHelper.delete(OfflineData.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("unknown uri " + uri);
        }

        if (null == selection || 0 != rowDeleted) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {


        final int match = uriMatcher.match(uri);
        int rowUpdated;
        dbHelper = new DBHelper(getContext()).getReadableDatabase();

        switch (match) {

            case SOURCE:
                rowUpdated = dbHelper.update(SourceTable.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            case OFFLINE_DATA:
                rowUpdated = dbHelper.update(OfflineData.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("unknown uri " + uri);
        }
        if (rowUpdated > 0) {

            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowUpdated;
    }
}
