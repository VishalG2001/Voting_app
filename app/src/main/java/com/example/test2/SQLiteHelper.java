package com.example.test2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "voting.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables for users and votes
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY, username TEXT, phone TEXT, email TEXT, password TEXT)");
        db.execSQL("CREATE TABLE votes (id INTEGER PRIMARY KEY, candidate TEXT, user_id INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade database schema if needed
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS votes");
        onCreate(db);
    }

    // Register new user
    public long registerUser(String username, String phone, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("phone", phone);
        values.put("email", email);
        values.put("password", password);
        long id = db.insert("users", null, values);
        db.close();
        return id;
    }

    // Authenticate user
    public boolean authenticateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{username, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return result;
    }

    // Check if user has already voted
    public boolean hasUserVoted(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM votes WHERE user_id=(SELECT id FROM users WHERE username=?)", new String[]{username});
        boolean hasVoted = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return hasVoted;
    }

    // Save vote in database
    public void saveVote(String username, String candidate) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("candidate", candidate);
            values.put("user_id", getUserId(username));
            long result = db.insert("votes", null, values);
            if (result == -1) {
                // Handle insertion failure
                // You can log an error message or throw an exception
            }
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                db.close(); // Close the database connection
            }
        }
    }


    // Get user id from username
    @SuppressLint("Range")
    private int getUserId(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM users WHERE username=?", new String[]{username});
        int userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex("id"));
        }
        cursor.close();
        db.close();
        return userId;
    }
}
