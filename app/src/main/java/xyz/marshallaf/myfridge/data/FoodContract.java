package xyz.marshallaf.myfridge.data;

import android.provider.BaseColumns;

/**
 * Specifies the schema of the database.
 *
 * Created by Andrew Marshall on 1/23/2017.
 */

public final class FoodContract {
    // non-instantiable
    private FoodContract() {}

    public static class FoodEntry implements BaseColumns {
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
        public static final int UNIT_ML = 1;
        public static final int UNIT_MG = 2;
    }
}
