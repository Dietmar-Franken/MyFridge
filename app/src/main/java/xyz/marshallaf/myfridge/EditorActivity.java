package xyz.marshallaf.myfridge;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import xyz.marshallaf.myfridge.data.FoodContract;
import xyz.marshallaf.myfridge.data.FoodDbHelper;
import xyz.marshallaf.myfridge.data.UnitContract;

/**
 * Activity to view and edit food entries.
 *
 * Created by Andrew Marshall on 1/26/2017.
 */

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private final String LOG_TAG = EditorActivity.class.getName();

    // if editing, true, otherwise false
    private boolean isEditing = true;

    // uri passed to intent when the activity started
    private Uri mUri;

    // database helper
    private FoodDbHelper mDbHelper;

    // member variables to easily access text fields
    private EditText mNameTextView;
    private EditText mAmountTextView;
    private EditText mStoreTextView;
    private EditText mPriceTextView;
    private EditText mExpTextView;
    private ArrayList<EditText> mEditTexts;
    private Spinner mUnitSpinner;
    private int mUnit;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // get db helper
        mDbHelper = new FoodDbHelper(this);

        // get references to textviews to read data from
        mNameTextView = (EditText) findViewById(R.id.edit_item_name);
        mAmountTextView = (EditText) findViewById(R.id.edit_item_amount);
        mStoreTextView = (EditText) findViewById(R.id.edit_item_store);
        mPriceTextView = (EditText) findViewById(R.id.edit_item_price);
        mExpTextView = (EditText) findViewById(R.id.edit_item_expiration);
        mUnitSpinner = (Spinner) findViewById(R.id.edit_item_unit);
        mEditTexts = new ArrayList<>();
        mEditTexts.add(mNameTextView);
        mEditTexts.add(mAmountTextView);
        mEditTexts.add(mStoreTextView);
        mEditTexts.add(mPriceTextView);
        mEditTexts.add(mExpTextView);
        
        setupSpinner();

        // check for a passed uri
        Uri uri = getIntent().getParcelableExtra(FoodContract.FoodEntry.FOOD_URI_KEY);
        if (uri != null) {
            toggleFields();
            mUri = uri;
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.editor_save:
                saveItem();
                finish();
                return true;
            case R.id.editor_edit:
                toggleFields();
                return true;
            case R.id.editor_delete:
                // call for delete dialog here
                showDeleteDialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDeleteDialog() {
        // build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete food item?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null) dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteItem();
                finish();
            }
        });

        // show the dialog
        builder.show();
    }

    private void deleteItem() {
        if (mUri != null) {
            getContentResolver().delete(mUri, null, null);
        }
    }

    private void saveItem() {
        // get values from editor fields
        String name = mNameTextView.getText().toString();
        String store = mStoreTextView.getText().toString();
        String priceString = mPriceTextView.getText().toString();
        String amountString = mAmountTextView.getText().toString();
        String expiration = mExpTextView.getText().toString();

        // TODO: don't let user save unless required fields are filled in

        // add values to object
        ContentValues values = new ContentValues();
        values.put(FoodContract.FoodEntry.COLUMN_NAME, name);
        values.put(FoodContract.FoodEntry.COLUMN_UNIT, mUnit);

        if (!TextUtils.isEmpty(expiration)) {
            // TODO: change this to a date entry field and parse to integer
            values.put(FoodContract.FoodEntry.COLUMN_EXPIRATION, 55556);
        }

        if (!TextUtils.isEmpty(store)) {
            values.put(FoodContract.FoodEntry.COLUMN_STORE, store);
        }

        // convert numeric values and add to object
        float amount = Float.parseFloat(amountString);
        values.put(FoodContract.FoodEntry.COLUMN_AMOUNT, amount);

        if (!TextUtils.isEmpty(priceString)) {
            float price = Float.parseFloat(priceString);
            values.put(FoodContract.FoodEntry.COLUMN_PRICE_PER, price);
        }

        // insert using contentresolver
        if (mUri == null) {
            getContentResolver().insert(FoodContract.FoodEntry.CONTENT_URI, values);
        } else {
            getContentResolver().update(mUri, values, null, null);
        }
    }

    private void toggleFields() {
        if (isEditing) {
            // all fields are active, make them inactive
            for (EditText view : mEditTexts) {
                disableEditText(view);
            }
        } else {
            // all fields are inactive, make them active
            for (EditText view : mEditTexts) {
                enableEditText(view);
            }
        }
        isEditing = !isEditing;
    }

    private void enableEditText(EditText view) {
        view.setEnabled(true);
    }

    private void disableEditText(EditText view) {
        view.setEnabled(false);
        view.setTextAppearance(this, R.style.EditTextDisabled);
        //view.getBackground().setColorFilter(Color.argb(0, 0, 0, 0), PorterDuff.Mode.SRC_IN);
    }

    private void setupSpinner() {
        final SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] columns = new String[] {UnitContract.UnitEntry.COLUMN_NAME};
        Cursor cursor = db.query(UnitContract.UnitEntry.TABLE_NAME, columns, null, null, null, null, UnitContract.UnitEntry._ID);
        final ArrayList<String> unitArray = new ArrayList<>();
        while (cursor.moveToNext()) {
            unitArray.add(cursor.getString(cursor.getColumnIndex(UnitContract.UnitEntry.COLUMN_NAME)));
        }
        Log.d(LOG_TAG, "First three units: " + unitArray.get(0) + ", " + unitArray.get(1) + ", " + unitArray.get(2));

        // create array adapter for spinner
        ArrayAdapter unitSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, unitArray);

        // set dropdown style
        unitSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // bind the adapter to the spinner
        mUnitSpinner.setAdapter(unitSpinnerAdapter);

        // set the on item click listener
        mUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                Log.d(LOG_TAG, "Position: " + position + ", ID: " + l);
                mUnit = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mUnit = 0;
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, mUri, null, null, null, UnitContract.UnitEntry._ID);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {
            // set name
            String name = data.getString(data.getColumnIndex(FoodContract.FoodEntry.COLUMN_NAME));
            mNameTextView.setText(name);

            // set amount
            String amount = String.valueOf(data.getFloat(data.getColumnIndex(FoodContract.FoodEntry.COLUMN_AMOUNT)));
            mAmountTextView.setText(amount);

            // set units
            int unit = data.getInt(data.getColumnIndex(FoodContract.FoodEntry.COLUMN_UNIT));
            mUnit = unit;
            mUnitSpinner.setSelection(mUnit);

            // set store
            String store = data.getString(data.getColumnIndex(FoodContract.FoodEntry.COLUMN_STORE));
            if (!TextUtils.isEmpty(store)) {
                mStoreTextView.setText(store);
            }
            // set expiration
            String expString = data.getString(data.getColumnIndex(FoodContract.FoodEntry.COLUMN_EXPIRATION));
            if (!TextUtils.isEmpty(expString)) {
                mExpTextView.setText(expString);
            }

            // set price per
            String priceString = data.getString(data.getColumnIndex(FoodContract.FoodEntry.COLUMN_PRICE_PER));
            if (!TextUtils.isEmpty(priceString)) {
                mPriceTextView.setText(priceString);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // null all the text views
        for (EditText view : mEditTexts) {
            view.setText(null);
        }
    }
}
