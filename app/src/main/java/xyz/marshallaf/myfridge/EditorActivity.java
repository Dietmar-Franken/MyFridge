package xyz.marshallaf.myfridge;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import xyz.marshallaf.myfridge.data.FoodContract;

/**
 * Activity to view and edit food entries.
 *
 * Created by Andrew Marshall on 1/26/2017.
 */

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    // if editing, true, otherwise false
    private boolean isEditing = true;

    // uri passed to intent when the activity started
    private Uri mUri;

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
                return true;
        }

        return super.onOptionsItemSelected(item);
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
        // create array adapter for spinner
        ArrayAdapter unitSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.unitSpinner, android.R.layout.simple_spinner_item);

        // set dropdown style
        unitSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // bind the adapter to the spinner
        mUnitSpinner.setAdapter(unitSpinnerAdapter);

        // set the on item click listener
        mUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    switch (selection) {
                        case "item":
                            mUnit = FoodContract.FoodEntry.UNIT_ITEM;
                            break;
                        case "volume":
                            mUnit = FoodContract.FoodEntry.UNIT_VOL;
                            break;
                        case "mass":
                            mUnit = FoodContract.FoodEntry.UNIT_MASS;
                            break;
                        default:
                            mUnit = FoodContract.FoodEntry.UNIT_ITEM;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mUnit = FoodContract.FoodEntry.UNIT_ITEM;
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, mUri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {
            // get the data
            String name = data.getString(data.getColumnIndex(FoodContract.FoodEntry.COLUMN_NAME));
            String amount = String.valueOf(data.getFloat(data.getColumnIndex(FoodContract.FoodEntry.COLUMN_AMOUNT)));
            String store = data.getString(data.getColumnIndex(FoodContract.FoodEntry.COLUMN_STORE));
            String expiration = String.valueOf(data.getInt(data.getColumnIndex(FoodContract.FoodEntry.COLUMN_EXPIRATION)));
            String price = String.valueOf(data.getFloat(data.getColumnIndex(FoodContract.FoodEntry.COLUMN_PRICE_PER)));

            // TODO: make sure there is something in the db for these fields before filling them so
            // you don't end up with 0 in the fields with nothing.

            // put the data in the layout
            mNameTextView.setText(name);
            mAmountTextView.setText(amount);
            mStoreTextView.setText(store);
            mExpTextView.setText(expiration);
            mPriceTextView.setText(price);
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
