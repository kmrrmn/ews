package com.rmn.ews.utiles;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rmn.ews.data.DBContract.*;

/**
 * Created by rmn on 29-08-2016.
 */

public class DBHelper extends SQLiteOpenHelper {
    Context mContext;
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "newsDB";
    SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        this.db = sqLiteDatabase;
        String createSourceTable = "CREATE TABLE " +
                SourceTable.TABLE_NAME + "(" +
                SourceTable.COLUMN_ID + " INTEGER PRIMARY KEY , " +
                SourceTable.COLUMN_SRC_NAME + " TEXT NOT NULL , " +
                SourceTable.COLUMN_SRC_URL_PRAM + " TEXT NOT NULL)";

        String createOfflineTable = "CREATE TABLE " +
                OfflineData.TABLE_NAME + "(" +
                OfflineData.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                OfflineData.COLUMN_TITLE + " TEXT NOT NULL , " +
                OfflineData.COLUMN_IMAGE + " BLOB," +
                OfflineData.COLUMN_IMAGE_URL + " TEXT ," +
                OfflineData.COLUMN_SOURCE + " TEXT NOT NULL , " +
                OfflineData.COLUMN_DESCRIPTION + " TEXT NOT NULL)";

        db.execSQL(createSourceTable);
        db.execSQL(createOfflineTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + SourceTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + OfflineData.TABLE_NAME);
    }

}
