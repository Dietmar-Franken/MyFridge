package xyz.marshallaf.myfridge;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.math.BigDecimal;

import xyz.marshallaf.myfridge.data.FoodContract.FoodEntry;

/**
 * Cursor adapter to display food list in MainActivity.
 *
 * Created by Andrew Marshall on 1/24/2017.
 */

class FoodCursorAdapter extends CursorAdapter {

    private Context mContext;

    FoodCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.food_list_item, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String foodName = cursor.getString(cursor.getColumnIndex(FoodEntry.COLUMN_NAME));
        int units = cursor.getInt(cursor.getColumnIndex(FoodEntry.COLUMN_UNIT));
        double amount = cursor.getDouble(cursor.getColumnIndex(FoodEntry.COLUMN_AMOUNT));

        String unitString = Utils.getUnitString(units, mContext);
        double amountConverted = Utils.convert(amount, units, false, mContext);
        BigDecimal amountRounded = new BigDecimal(amountConverted).setScale(2, BigDecimal.ROUND_HALF_UP);

        ((TextView) view.findViewById(R.id.food_item_name)).setText(foodName);
        ((TextView) view.findViewById(R.id.food_item_amount)).setText(amountRounded.toString() + " " + unitString);
    }
}
