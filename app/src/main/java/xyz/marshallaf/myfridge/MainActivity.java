package xyz.marshallaf.myfridge;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import xyz.marshallaf.myfridge.data.FoodContract;
import xyz.marshallaf.myfridge.data.FoodDbHelper;

public class MainActivity extends AppCompatActivity {

    private FoodDbHelper mDbHelper;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        
        mDbHelper = new FoodDbHelper(this);

        // set up the database
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // get a cursor of all the data
        Cursor cursor = db.query(FoodContract.FoodEntry.TABLE_NAME, null, null, null, null, null, null);

        // create the cursor adapter
        FoodCursorAdapter adapter = new FoodCursorAdapter(this, cursor);

        // assign the cursor to the listview
        ((ListView) findViewById(R.id.list_view)).setAdapter(adapter);
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
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FoodContract.FoodEntry.COLUMN_NAME, "Eggs");
        values.put(FoodContract.FoodEntry.COLUMN_AMOUNT, 12);
        values.put(FoodContract.FoodEntry.COLUMN_UNIT, FoodContract.FoodEntry.UNIT_ITEM);

        db.insert(FoodContract.FoodEntry.TABLE_NAME, null, values);
    }
}
