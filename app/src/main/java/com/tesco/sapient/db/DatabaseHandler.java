package com.tesco.sapient.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tesco.sapient.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper implements UserRepository {

    // All Static variables
    private static final String TAG = DatabaseHandler.class.getSimpleName();
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Tesco";
    // User table name
    private static final String TABLE_USER = "user_info";

    // User Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "userName";
    private static final String KEY_PASSWORD = "userPassword";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_USERNAME + " TEXT, "
                + KEY_PASSWORD + " TEXT )";
        db.execSQL(CREATE_USER_TABLE);

        // Inserting dummy user record
        insertUsers(dummyUserList(), db);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Check user exists or not in user table
    public UserModel checkUser(UserModel userModel) {
        Cursor cursor = null;
        SQLiteDatabase db = null;
        UserModel user = null;
        try {
            db = this.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_USERNAME + "=? AND " + KEY_PASSWORD + "=?";
            cursor = db.rawQuery(query, new String[]{userModel.getUserName(), userModel.getPassword()});
            //Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    user = new UserModel();
                    user.setUserId(cursor.getInt(0));
                    user.setUserName(cursor.getString(1));
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return user;
    }


    public void insertUsers(List<UserModel> list, SQLiteDatabase db) {
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (UserModel userModel : list) {
                values.put(KEY_USERNAME, userModel.getUserName());
                values.put(KEY_PASSWORD, userModel.getPassword());
                db.insert(TABLE_USER, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        Log.d(TAG, "Dummy Record inserted");
    }

    private List<UserModel> dummyUserList() {
        List<UserModel> list = new ArrayList<>();
        UserModel userModel = null;
        userModel = new UserModel("test", "test");
        list.add(userModel);
        userModel = new UserModel("akhilesh", "akhilesh");
        list.add(userModel);
        userModel = new UserModel("sushant", "sushant");
        list.add(userModel);
        userModel = new UserModel("sid", "sid");
        list.add(userModel);
        return list;
    }

    @Override
    public UserModel authenticate(UserModel userModel) {
        return checkUser(userModel);
    }
}