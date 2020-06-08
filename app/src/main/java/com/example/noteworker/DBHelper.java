package com.example.noteworker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelper {

    private static final String DB_NAME="notes.db";
    private static final int DB_VERSION=12;
    public static final String NOTES_TABLE = "notes";

    public static final String NOTE_ID = "id";
    public static final String NOTE_NAME = "name";
    public static final String NOTE_DESCRIPTION = "description";
    public static final String NOTE_DATE = "date";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_NAME = 1;
    private static final int NUM_COLUMN_DESC = 2;
    private static final int NUM_COLUMN_DATE = 3;


    private SQLiteDatabase db;

    public DBHelper(Context context) {
        SQLHelper dbHelper = new SQLHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public static void getInstance() {
    }

    public long insert(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_NAME, note.getName());
        contentValues.put(NOTE_DESCRIPTION, note.getDescription());
        contentValues.put(NOTE_DATE, note.getDate());
        return db.insert(NOTES_TABLE, null, contentValues);
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<Note>();

        Cursor cursor = db.query(NOTES_TABLE, null, null, null, null, null, null);

        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                long id = cursor.getLong(NUM_COLUMN_ID);
                String name = cursor.getString(NUM_COLUMN_NAME);
                String desc = cursor.getString(NUM_COLUMN_DESC);
                String date= cursor.getString(NUM_COLUMN_DATE);
                Note note=new Note(id,name,desc,date);
                notes.add(note);
            } while (cursor.moveToNext());
        }
      return notes;
    }

    public void deleteNote(Note note){
            db.delete(NOTES_TABLE, NOTE_ID + "="+note.getId(), null);
    }

    public int update(Note note) {
        ContentValues cv=new ContentValues();
        cv.put(NOTE_NAME, note.getName());
        cv.put(NOTE_DESCRIPTION, note.getDescription());
        return db.update(NOTES_TABLE, cv, NOTE_ID + " = ?",new String[] { String.valueOf(note.getId())});
    }
    private class SQLHelper extends SQLiteOpenHelper {

        public SQLHelper(@Nullable Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query="CREATE TABLE " + NOTES_TABLE +
                    "(" + NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    NOTE_NAME + " TEXT NOT NULL, " +
                    NOTE_DESCRIPTION + " TEXT, "+
                    NOTE_DATE + " TEXT)";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE);
            onCreate(db);
        }
    }

}