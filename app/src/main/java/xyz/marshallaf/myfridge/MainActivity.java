package xyz.marshallaf.myfridge;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import xyz.marshallaf.myfridge.data.FoodContract;
import xyz.marshallaf.myfridge.data.FoodDbHelper;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private FoodDbHelper mDbHelper;
    private FoodCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        
        mDbHelper = new FoodDbHelper(this);

        // create the cursor adapter
        mCursorAdapter = new FoodCursorAdapter(this, null);

        // assign the cursor to the listview
        ListView foodList = (ListView) findViewById(R.id.list_view);
        foodList.setAdapter(mCursorAdapter);

        // set click listener for listview
        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Uri uri = ContentUris.withAppendedId(FoodContract.FoodEntry.CONTENT_URI, id);
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                intent.putExtra(FoodContract.FoodEntry.FOOD_URI_KEY, uri);
                startActivity(intent);
            }
        });

        // initialize the loader
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_dummy_data) {
            // insert dummy data (for debugging)
            insertDummyData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void insertDummyData() {
        ContentValues values = new ContentValues();
        values.put(FoodContract.FoodEntry.COLUMN_NAME, "Eggs");
        values.put(FoodContract.FoodEntry.COLUMN_AMOUNT, 12);
        values.put(FoodContract.FoodEntry.COLUMN_UNIT, FoodContract.FoodEntry.UNIT_ITEM);

        getContentResolver().insert(FoodContract.FoodEntry.CONTENT_URI, values);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[] {
                FoodContract.FoodEntry._ID,
                FoodContract.FoodEntry.COLUMN_NAME,
                FoodContract.FoodEntry.COLUMN_AMOUNT,
                FoodContract.FoodEntry.COLUMN_UNIT
        };
        return new CursorLoader(this, FoodContract.FoodEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // remove reference to cursor
        mCursorAdapter.swapCursor(null);
    }
}
