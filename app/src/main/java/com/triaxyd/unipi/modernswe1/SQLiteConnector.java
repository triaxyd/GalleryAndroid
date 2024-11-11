package com.triaxyd.unipi.modernswe1;

import android.database.Cursor;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteConnector extends SQLiteOpenHelper {

    private static final String DB_NAME = "paintings.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "paintings";

    private static final String IMG_REFERENCE = "img_reference";

    private static final String DESCRIPTION = "description";


    public SQLiteConnector(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + IMG_REFERENCE + " TEXT PRIMARY KEY, "
                + DESCRIPTION + " TEXT)";

        db.execSQL(query);
    }

    // when db needs upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //check if the table exists already
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    // add paintings
    public Status addPainting(String imgReference, String description) {
        if (imgReference == null || description == null) return Status.FAILURE;

        SQLiteDatabase db = this.getWritableDatabase();

        // check if the painting already exists
        String query = "SELECT 1 FROM " + TABLE_NAME + " WHERE " + IMG_REFERENCE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[] { imgReference });

        boolean exists = cursor.moveToFirst();
        cursor.close();

        if (exists) {
            db.close();
            return Status.ALREADY_EXISTS;
        }

        // insert new painting if it doesn't already exist
        ContentValues values = new ContentValues();
        values.put(IMG_REFERENCE, imgReference);
        values.put(DESCRIPTION, description);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        // return SUCCESS if the painting was saved, else FAILURE
        return result != -1 ? Status.SUCCESS : Status.FAILURE;
    }


    // retrieve painting
    public Painting retrievePainting(String imgReference) {
        if (imgReference == null) return null;

        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = new String[] { imgReference };
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + IMG_REFERENCE + "=?";

        Cursor cursor = db.rawQuery(query, args);
        Painting painting = null;

        if (cursor.moveToFirst()) {
            int imgRefIndex = cursor.getColumnIndex(IMG_REFERENCE);
            int descIndex = cursor.getColumnIndex(DESCRIPTION);

            if (imgRefIndex != -1 && descIndex != -1) {
                String imgRef = cursor.getString(imgRefIndex);
                String description = cursor.getString(descIndex);
                painting = new Painting(imgRef, description);
            }
        }

        cursor.close();
        db.close();

        return painting;
    }


}