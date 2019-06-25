package com.example.lenovo.pecs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gobinath on 4/7/16.
 */
public class DatabaseAccess {
    private SQLiteOpenHelper openHelper; //call function
    private SQLiteDatabase database; //sqlite db
    private static DatabaseAccess instance;

    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * parameter context
     */
    public DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

   //open database connection
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    //close database connection
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    //Retrieve all data from database
    public Cursor getData(String sql){
        database= openHelper.getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public Cursor getData1(String sql){
        database= openHelper.getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    //display name and image in gridview at General.this
    public Cursor getImage(String id){
        Cursor cursor;
        String selectQuery = "SELECT RECIPE.id, RECIPE.name, RECIPE.image FROM RECIPE INNER JOIN TYPE WHERE RECIPE.type_id=TYPE.id AND RECIPE.type_id=\""+ id+ "\" " ;
        database= openHelper.getReadableDatabase();
        cursor = database.rawQuery(selectQuery,null);
        return cursor;
    }

    public Cursor getInfo(String id, String id2){
        Cursor cursor;
        String selectQuery = "SELECT RECIPE.name FROM RECIPE INNER JOIN TYPE WHERE RECIPE.type_id=TYPE.id AND RECIPE.type_id=\""+id+ "\" AND RECIPE.id=\""+id2+"\"" ;
        database= openHelper.getReadableDatabase();
        cursor = database.rawQuery(selectQuery,null);
        return cursor;
    }

    public Cursor get(String id){
        Cursor cursor;
        String selectQuery = "SELECT id FROM RECIPE where type_id=\""+ id+ "\" " ;
        database= openHelper.getReadableDatabase();
        cursor = database.rawQuery(selectQuery,null);
        return cursor;
    }



       //Insert data in things
    public void insertData( String name, byte[] image){
        database= openHelper.getWritableDatabase();
        String sql = "INSERT INTO TYPE VALUES (NULL, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

//        statement.bindString(0, id);
        statement.bindString(1, name);
        statement.bindBlob(2, image);

        statement.executeInsert();
    }


    //Update image in things
    public void updateData(String name, byte[] image,String id) {
        database= openHelper.getWritableDatabase();

        String sql = "UPDATE RECIPE SET name = ?, image=? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindBlob(2, image);
        statement.bindString(3, id);

        statement.execute();
        database.close();
    }

    public  void deleteData(String id) {
        database= openHelper.getWritableDatabase();

        String sql = "DELETE FROM RECIPE WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, id);

        statement.execute();
        database.close();
    }

    public  void deleteData1(String id) {
        database= openHelper.getWritableDatabase();

        String sql = "DELETE FROM GENERAL WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, id);

        statement.execute();
        database.close();
    }

}