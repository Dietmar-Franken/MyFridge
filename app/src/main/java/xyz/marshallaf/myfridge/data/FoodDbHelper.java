package xyz.marshallaf.myfridge.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import xyz.marshallaf.myfridge.R;
import xyz.marshallaf.myfridge.data.FoodContract.FoodEntry;

/**
 * Helper to create and manage versions of database.
 *
 * Created by Andrew Marshall on 1/23/2017.
 */

public class FoodDbHelper extends SQLiteOpenHelper {
    private Context mContext;

    // relating to database itself
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "kitchen.db";

    // sql commands
    private static final String SQL_CREATE_FOOD_TABLE =
            "CREATE TABLE " + FoodEntry.TABLE_NAME + " (" +
            FoodEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FoodEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            FoodEntry.COLUMN_UNIT + " INTEGER NOT NULL, " +
            FoodEntry.COLUMN_AMOUNT + " REAL NOT NULL, " +
            FoodEntry.COLUMN_PRICE_PER + " REAL, " +
            FoodEntry.COLUMN_EXPIRATION + " INTEGER, " +
            FoodEntry.COLUMN_STORE + " TEXT, " +
            FoodEntry.COLUMN_PHOTO + " TEXT);";

    private static final String SQL_CREATE_UNITS_TABLE =
            "CREATE TABLE " + UnitContract.UnitEntry.TABLE_NAME + " (" +
            UnitContract.UnitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            UnitContract.UnitEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            UnitContract.UnitEntry.COLUMN_CONVERT + " REAL NOT NULL);";

    public FoodDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create empty databases
        db.execSQL(SQL_CREATE_FOOD_TABLE);
        db.execSQL(SQL_CREATE_UNITS_TABLE);

        // add entries to units db
        ArrayList<ContentValues> valueList = new ArrayList<>();
        ContentValues cv = new ContentValues();

        // item
        cv.put(UnitContract.UnitEntry.COLUMN_NAME, mContext.getString(R.string.item));
        cv.put(UnitContract.UnitEntry.COLUMN_CONVERT, UnitContract.UnitEntry.UNIT_ITEM);
        valueList.add(new ContentValues(cv));

        // milliliter
        cv.put(UnitContract.UnitEntry.COLUMN_NAME, mContext.getString(R.string.milliliter));
        cv.put(UnitContract.UnitEntry.COLUMN_CONVERT, UnitContract.UnitEntry.ML_IN_ML);
        valueList.add(new ContentValues(cv));

        // liter
        cv.put(UnitContract.UnitEntry.COLUMN_NAME, mContext.getString(R.string.liter));
        cv.put(UnitContract.UnitEntry.COLUMN_CONVERT, UnitContract.UnitEntry.ML_IN_L);
        valueList.add(new ContentValues(cv));

        // fluid oz
        cv.put(UnitContract.UnitEntry.COLUMN_NAME, mContext.getString(R.string.fluid_oz));
        cv.put(UnitContract.UnitEntry.COLUMN_CONVERT, UnitContract.UnitEntry.ML_IN_FLOZ);
        valueList.add(new ContentValues(cv));

        // pint
        cv.put(UnitContract.UnitEntry.COLUMN_NAME, mContext.getString(R.string.pint));
        cv.put(UnitContract.UnitEntry.COLUMN_CONVERT, UnitContract.UnitEntry.ML_IN_PINT);
        valueList.add(new ContentValues(cv));

        // quart
        cv.put(UnitContract.UnitEntry.COLUMN_NAME, mContext.getString(R.string.quart));
        cv.put(UnitContract.UnitEntry.COLUMN_CONVERT, UnitContract.UnitEntry.ML_IN_QUART);
        valueList.add(new ContentValues(cv));

        // gallon
        cv.put(UnitContract.UnitEntry.COLUMN_NAME, mContext.getString(R.string.gallon));
        cv.put(UnitContract.UnitEntry.COLUMN_CONVERT, UnitContract.UnitEntry.ML_IN_GALLON);
        valueList.add(new ContentValues(cv));

        // teaspoon
        cv.put(UnitContract.UnitEntry.COLUMN_NAME, mContext.getString(R.string.teaspoon));
        cv.put(UnitContract.UnitEntry.COLUMN_CONVERT, UnitContract.UnitEntry.ML_IN_TSP);
        valueList.add(new ContentValues(cv));

        // tablespoon
        cv.put(UnitContract.UnitEntry.COLUMN_NAME, mContext.getString(R.string.tablespoon));
        cv.put(UnitContract.UnitEntry.COLUMN_CONVERT, UnitContract.UnitEntry.ML_IN_TBSP);
        valueList.add(new ContentValues(cv));

        // cup
        cv.put(UnitContract.UnitEntry.COLUMN_NAME, mContext.getString(R.string.cup));
        cv.put(UnitContract.UnitEntry.COLUMN_CONVERT, UnitContract.UnitEntry.ML_IN_CUP);
        valueList.add(new ContentValues(cv));

        // gram
        cv.put(UnitContract.UnitEntry.COLUMN_NAME, mContext.getString(R.string.gram));
        cv.put(UnitContract.UnitEntry.COLUMN_CONVERT, UnitContract.UnitEntry.G_IN_G);
        valueList.add(new ContentValues(cv));

        // milligram
        cv.put(UnitContract.UnitEntry.COLUMN_NAME, mContext.getString(R.string.milligram));
        cv.put(UnitContract.UnitEntry.COLUMN_CONVERT, UnitContract.UnitEntry.G_IN_MG);
        valueList.add(new ContentValues(cv));

        // kilogram
        cv.put(UnitContract.UnitEntry.COLUMN_NAME, mContext.getString(R.string.kilogram));
        cv.put(UnitContract.UnitEntry.COLUMN_CONVERT, UnitContract.UnitEntry.G_IN_KG);
        valueList.add(new ContentValues(cv));

        // ounce
        cv.put(UnitContract.UnitEntry.COLUMN_NAME, mContext.getString(R.string.ounce));
        cv.put(UnitContract.UnitEntry.COLUMN_CONVERT, UnitContract.UnitEntry.G_IN_OZ);
        valueList.add(new ContentValues(cv));

        // pound
        cv.put(UnitContract.UnitEntry.COLUMN_NAME, mContext.getString(R.string.pound));
        cv.put(UnitContract.UnitEntry.COLUMN_CONVERT, UnitContract.UnitEntry.G_IN_POUND);
        valueList.add(new ContentValues(cv));

        for (ContentValues values : valueList) {
            db.insert(UnitContract.UnitEntry.TABLE_NAME, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: this probably needs something in it
    }
}
