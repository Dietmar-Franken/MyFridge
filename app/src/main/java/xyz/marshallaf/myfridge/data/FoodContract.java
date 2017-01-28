package xyz.marshallaf.myfridge.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Specifies the schema of the database.
 *
 * Created by Andrew Marshall on 1/23/2017.
 */

public final class FoodContract {
    // non-instantiable
    private FoodContract() {}

    // base content uri
    public static final String CONTENT_AUTHORITY = "xyz.marshallaf.myfridge";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // uri path for food table
    public static final String PATH_FOOD = "food";

    public static class FoodEntry implements BaseColumns {
        // full content uri for food table
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FOOD);

        // key for parceling
        public static final String FOOD_URI_KEY = "data_food_item";

        // database table name
        public static final String TABLE_NAME = "food";

        // column names
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_UNIT = "unit";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_PRICE_PER = "priceper";
        public static final String COLUMN_STORE = "store";
        public static final String COLUMN_EXPIRATION = "expiration";
        public static final String COLUMN_PHOTO = "photo";

        // constants for unit
        public static final int UNIT_ITEM = 0;
        public static final int UNIT_VOL = 1;
        public static final int UNIT_MASS = 2;
    }
}
