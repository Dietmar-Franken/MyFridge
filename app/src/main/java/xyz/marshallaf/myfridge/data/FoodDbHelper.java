package xyz.marshallaf.myfridge.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import xyz.marshallaf.myfridge.data.FoodContract.FoodEntry;

/**
 * Helper to create and manage versions of database.
 *
 * Created by Andrew Marshall on 1/23/2017.
 */

public class FoodDbHelper extends SQLiteOpenHelper {
    // relating to database itself
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "kitchen.db";

    // sql commands
    private static final String SQL_CREATE_FOOD_TABLE =
            "CREATE TABLE " + FoodEntry.TABLE_NAME + " (" +
            FoodEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FoodEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            FoodEntry.COLUMN_UNIT_TYPE + " INTEGER NOT NULL, " +
            FoodEntry.COLUMN_UNIT_PREF + " INTEGER NOT NULL, " +
            FoodEntry.COLUMN_AMOUNT + " REAL NOT NULL, " +
            FoodEntry.COLUMN_PRICE_PER + " REAL, " +
            FoodEntry.COLUMN_EXPIRATION + " INTEGER, " +
            FoodEntry.COLUMN_STORE + " TEXT, " +
            FoodEntry.COLUMN_PHOTO + " TEXT);";

    public FoodDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FOOD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: this probably needs something in it
    }
}
