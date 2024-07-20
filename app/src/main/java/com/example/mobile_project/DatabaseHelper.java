package com.example.mobile_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "hiking_db";

    Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Hiking.CREATE_TABLE);
        sqLiteDatabase.execSQL(Observation.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Hiking.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Observation.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertHiking(String name, String location, String date, String length, String trail,
                             String parking, String level, String weather, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Hiking.COLUMN_NAME, name);
        values.put(Hiking.COLUMN_LOCATION, location);
        values.put(Hiking.COLUMN_DATE, date);
        values.put(Hiking.COLUMN_LENGTH, length);
        values.put(Hiking.COLUMN_TRAIL, trail);
        values.put(Hiking.COLUMN_PARKING, parking);
        values.put(Hiking.COLUMN_LEVEL, level);
        values.put(Hiking.COLUMN_WEATHER, weather);
        values.put(Hiking.COLUMN_DESC, desc);
        long id = db.insert(Hiking.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public int updateHiking(Hiking hiking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Hiking.COLUMN_NAME, hiking.getName());
        values.put(Hiking.COLUMN_LOCATION, hiking.getLocation());
        values.put(Hiking.COLUMN_DATE, hiking.getDate());
        values.put(Hiking.COLUMN_LENGTH, hiking.getLength());
        values.put(Hiking.COLUMN_TRAIL, hiking.getTrail());
        values.put(Hiking.COLUMN_PARKING, hiking.getParking());
        values.put(Hiking.COLUMN_LEVEL, hiking.getLevel());
        values.put(Hiking.COLUMN_WEATHER, hiking.getWeather());
        values.put(Hiking.COLUMN_DESC, hiking.getDesc());
        int rowUpdate = db.update(Hiking.TABLE_NAME, values, Hiking.COLUMN_ID + "=?",
                new String[]{String.valueOf(hiking.getId())});
        db.close();
        return rowUpdate;
    }

    public void deleteHiking(Hiking hiking) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Hiking.TABLE_NAME, Hiking.COLUMN_ID + "=?",
                new String[]{String.valueOf(hiking.getId())});
        db.close();
    }

    public Hiking getHiking(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Hiking.TABLE_NAME,
                new String[]{
                        Hiking.COLUMN_ID,
                        Hiking.COLUMN_NAME,
                        Hiking.COLUMN_LOCATION,
                        Hiking.COLUMN_DATE,
                        Hiking.COLUMN_LENGTH,
                        Hiking.COLUMN_TRAIL,
                        Hiking.COLUMN_PARKING,
                        Hiking.COLUMN_LEVEL,
                        Hiking.COLUMN_WEATHER,
                        Hiking.COLUMN_DESC},
                Hiking.COLUMN_ID + "=?",
                new String[]{
                        String.valueOf(id)
                }, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        Hiking hiking = new Hiking(
                cursor.getInt(cursor.getColumnIndexOrThrow(Hiking.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_LOCATION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_DATE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_LENGTH)),
                cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_TRAIL)),
                cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_PARKING)),
                cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_LEVEL)),
                cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_WEATHER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_DESC))
        );
        cursor.close();
        return hiking;
    }

    public ArrayList<Hiking> getAllHiking() {
        ArrayList<Hiking> hikings = new ArrayList<>();
        String selectQuery = " SELECT * FROM " + Hiking.TABLE_NAME + " ORDER BY " +
                Hiking.COLUMN_ID + " DESC ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Hiking hiking = new Hiking();
                hiking.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Hiking.COLUMN_ID)));
                hiking.setName(cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_NAME)));
                hiking.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_LOCATION)));
                hiking.setDate(cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_DATE)));
                hiking.setLength(cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_LENGTH)));
                hiking.setTrail(cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_TRAIL)));
                hiking.setParking(cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_PARKING)));
                hiking.setLevel(cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_LEVEL)));
                hiking.setWeather(cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_WEATHER)));
                hiking.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_DESC)));
            } while (cursor.moveToNext());
        }
        db.close();
        return hikings;
    }

    public List<Hiking> searchHiking(String key) {
        SQLiteDatabase db = this.getReadableDatabase();
        String querry = "SELECT * FROM " + Hiking.TABLE_NAME + " WHERE "
                + Hiking.COLUMN_NAME + " LIKE '%" + key + "%'";
        List<Hiking> hikings = new ArrayList<>();
        Cursor cursor = db.rawQuery(querry, null);
        if (cursor.moveToFirst()) {
            do {
                Hiking hiking = new Hiking();
                hiking.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Hiking.COLUMN_ID)));
                hiking.setName(cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_NAME)));
                hiking.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_LOCATION)));
                hiking.setDate(cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_DATE)));
                hiking.setLength(cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_LENGTH)));
                hiking.setTrail(cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_TRAIL)));
                hiking.setParking(cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_PARKING)));
                hiking.setLevel(cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_LEVEL)));
                hiking.setWeather(cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_WEATHER)));
                hiking.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(Hiking.COLUMN_DESC)));
                hikings.add(hiking);
            } while (cursor.moveToNext());
        }
        db.close();
        return hikings;

    }


    // Observation table methods
    public long insertObservation(String hikeId, String observation, String time, String additionalComments) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Observation.COLUMN_OBSERVATION, observation);
        values.put(Observation.COLUMN_TIME, time);
        values.put(Observation.COLUMN_COMMENTS, additionalComments);
        values.put(Observation.COLUMN_HIKING_ID, hikeId);
        long id = db.insert(Observation.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public int updateObservation(Observation observation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Observation.COLUMN_OBSERVATION, observation.getObservation());
        values.put(Observation.COLUMN_TIME, observation.getTime());
        values.put(Observation.COLUMN_COMMENTS, observation.getComments());
        values.put(Observation.COLUMN_HIKING_ID, observation.getHikingId());
        int rowUpdate = db.update(Observation.TABLE_NAME, values, Observation.COLUMN_OBSERVATION + "=?",
                new String[]{String.valueOf(observation.getObservation())});
        db.close();
        return rowUpdate;
    }

    public void deleteObservation(Observation observation) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Observation.TABLE_NAME, Observation.COLUMN_OBSERVATION + "=?",
                new String[]{String.valueOf(observation.getObservation())});
        db.close();
    }

    public Observation getObservation(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Observation.TABLE_NAME,
                new String[]{
                        Observation.OBSERVATION_ID,
                        Observation.COLUMN_OBSERVATION,
                        Observation.COLUMN_TIME,
                        Observation.COLUMN_COMMENTS,
                        Observation.COLUMN_HIKING_ID},
                Observation.OBSERVATION_ID + "=?",
                new String[]{
                        String.valueOf(id)
                }, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        Observation observation = new Observation(
                cursor.getInt(cursor.getColumnIndexOrThrow(Observation.OBSERVATION_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(Observation.COLUMN_OBSERVATION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Observation.COLUMN_TIME)),
                cursor.getString(cursor.getColumnIndexOrThrow(Observation.COLUMN_COMMENTS)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Observation.COLUMN_HIKING_ID))
        );
        cursor.close();
        return observation;
    }

    public ArrayList<Observation> getAllObservations() {
        ArrayList<Observation> observations = new ArrayList<>();
        /*String selectQuery = " SELECT * FROM " + Observation.TABLE_NAME + " ORDER BY " +
                Observation.OBSERVATION_ID + " DESC ";*/

        String selectQuery = "SELECT * FROM " + Observation.TABLE_NAME +
                " WHERE " + Observation.COLUMN_HIKING_ID + " = " + Hiking.COLUMN_ID +
                " ORDER BY " + Observation.OBSERVATION_ID + " DESC ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Observation observation = new Observation();
                observation.setObservationId(cursor.getInt(cursor.getColumnIndexOrThrow(Observation.OBSERVATION_ID)));
                observation.setObservation(cursor.getString(cursor.getColumnIndexOrThrow(Observation.COLUMN_OBSERVATION)));
                observation.setTime(cursor.getString(cursor.getColumnIndexOrThrow(Observation.COLUMN_TIME)));
                observation.setComments(cursor.getString(cursor.getColumnIndexOrThrow(Observation.COLUMN_COMMENTS)));
                observation.setHikingId(cursor.getInt(cursor.getColumnIndexOrThrow(Observation.COLUMN_HIKING_ID)));
                observations.add(observation);
            } while (cursor.moveToNext());
        }
        db.close();
        return observations;
    }
}

