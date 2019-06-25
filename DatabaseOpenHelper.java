package com.example.lenovo.pecs;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

//Calling for ImageDB.db from asset/databases folder

public class DatabaseOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "Recipe.db"; //kena tally dgn nama db
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}