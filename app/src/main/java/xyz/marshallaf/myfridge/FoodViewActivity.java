package xyz.marshallaf.myfridge;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Calendar;

import xyz.marshallaf.myfridge.data.FoodContract;
import xyz.marshallaf.myfridge.data.FoodDbHelper;

/**
 * Created by Andrew Marshall on 2/8/2017.
 */

public class FoodViewActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private final String LOG_TAG = FoodViewActivity.class.getName();

    private Uri mUri;

    private FoodDbHelper mDbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        // get the passed Uri
        Uri uri = getIntent().getParcelableExtra(FoodContract.FoodEntry.FOOD_URI_KEY);
        if (uri != null) {
            mUri = uri;
            getSupportLoaderManager().initLoader(0, null, this);
        } else {
            // something has gone wrong, so just return to the food list
            Log.e(LOG_TAG, "No Uri passed to View Activity.");
            finish();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, mUri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // check for data
        if (data.moveToFirst()) {
            // set name
            ((TextView) findViewById(R.id.view_food_name)).setText(data.getString(data.getColumnIndex(FoodContract.FoodEntry.COLUMN_NAME)));

            // TODO: may want to have a Util function for getting the full string together
            // get unit code and string
            int unit = data.getInt(data.getColumnIndex(FoodContract.FoodEntry.COLUMN_UNIT));
            String unitString = Utils.getUnitString(unit, this);

            // get amount
            double amount = data.getDouble(data.getColumnIndex(FoodContract.FoodEntry.COLUMN_AMOUNT));
            amount = Utils.convert(amount, unit, false, this);
            BigDecimal amountBd = new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP);
            String amountString = amountBd.toString();

            // set amount
            String fullAmountString = amountString + " " + unitString;
            ((TextView) findViewById(R.id.view_food_amount)).setText(fullAmountString);

            // check and set expiration
            String expString = data.getString(data.getColumnIndex(FoodContract.FoodEntry.COLUMN_EXPIRATION));
            if (!TextUtils.isEmpty(expString)) {
                Calendar now = Calendar.getInstance();
                now.setTimeInMillis(Long.parseLong(expString));
                String dateString = (now.get(Calendar.MONTH)+1) + "/" + now.get(Calendar.DAY_OF_MONTH) + "/" + now.get(Calendar.YEAR);
                ((TextView) findViewById(R.id.view_food_expires)).setText(dateString);
            }

            // check and set price
            String priceString = data.getString(data.getColumnIndex(FoodContract.FoodEntry.COLUMN_PRICE_PER));
            if (!TextUtils.isEmpty(priceString)) {
                ((TextView) findViewById(R.id.view_food_price)).setText(priceString);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
