package dreamteamuk.touristapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ItineraryActivity extends AppCompatActivity {

    private static final String TAG = "ItineraryActivity";
    // Number of items the view can hold
    private int mCount;
    // member that holds the adapter for the itinerary list
    private ItineraryAdapter mAdapter;
    // member that holds recyclerview of the itinerary list
    private RecyclerView mItineraryList;
    // member that holds the database
    private SQLiteDatabase mItineraryDb;
    // Edit text field for place name
    private EditText mNewPlaceNameEditText;
    // Edit text field for priority
    private EditText mNewPriorityNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Trace Activity lifecycle
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_itinerary);

        // Add floating button to add Itinerary items
        //  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        // Add edit text to add data
        mNewPlaceNameEditText = (EditText) findViewById(R.id.edit_place_name);
        mNewPriorityNameEditText = (EditText) findViewById(R.id.edit_priority);

        mItineraryList = (RecyclerView) findViewById(R.id.rv_itinerary);

    /*    fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create an itinerary intent to start Activity that adds a new place to Itinerary
                Intent intentItinerary;
                intentItinerary = new Intent(ItineraryActivity.this, AddItineraryActivity.class);

                // Verify that the intent will resolve to an activity
                if (intentItinerary.resolveActivity(getPackageManager()) != null) {
                    startActivity(intentItinerary);
                }

            }
        });*/


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mItineraryList.setLayoutManager(layoutManager);

        // Create an instance of DbHelper to create the database and tables
        ItineraryListDbHelper dbHelper = new ItineraryListDbHelper(this);
        // Create a writable database
        mItineraryDb = dbHelper.getWritableDatabase();

        Cursor cursor = getAllItineraryData();

        mAdapter = new ItineraryAdapter(this, cursor);
        mItineraryList.setAdapter(mAdapter);


        // Add feature to delete itinerary items by using the swipe functionality.

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long) viewHolder.itemView.getTag();
                removeItinerary(id);
                mAdapter.swapCursor(getAllItineraryData());

            }
        }).attachToRecyclerView(mItineraryList);

    }


    /**
     * Log Activity lifecycle
     */

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }


    /**
     * Add item to itinerary list
     */
    public void addToItineraryList(View view) {

        // If the edit text fields are empty exit method
        if (mNewPriorityNameEditText.getText().length() == 0 || mNewPlaceNameEditText.getText().length() == 0) {
            return;
        }

        String editPlacename = mNewPlaceNameEditText.getText().toString();
        String editPriority = mNewPriorityNameEditText.getText().toString();

        addItinerary(editPlacename, editPriority);

        mAdapter.swapCursor(getAllItineraryData());

        mNewPlaceNameEditText.clearFocus();
        mNewPlaceNameEditText.getText().clear();
        mNewPriorityNameEditText.getText().clear();
    }

    /**
     * Adds a new record to Itinerary table
     */
    public long addItinerary(String name, String priority) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ItineraryListContract.ItineraryListEntry.COLUMN_PLACE_NAME, name);
        contentValues.put(ItineraryListContract.ItineraryListEntry.COLUMN_PRIORITY, priority);

        long newRowId = mItineraryDb.insert(ItineraryListContract.ItineraryListEntry.TABLE_NAME, null, contentValues);

        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving itinerary", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Itinerary saved", Toast.LENGTH_SHORT).show();
        }

        return newRowId;
    }


    /**
     * Removes a record from the itinerary list table
     */
    public boolean removeItinerary(long id) {

        return mItineraryDb.delete(ItineraryListContract.ItineraryListEntry.TABLE_NAME, ItineraryListContract.ItineraryListEntry._ID + "=" + id, null) > 0;

    }

    /**
     * Method for displaying all data in itinerary list table
     */
    private Cursor getAllItineraryData() {

        return mItineraryDb.query(ItineraryListContract.ItineraryListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ItineraryListContract.ItineraryListEntry.COLUMN_PLACE_NAME
        );
    }

    /**
     * Set up shared preferences
     */
    public void setUpSharedPreferences() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }


    //Create an options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuItemSelected = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_search:
                Context context = ItineraryActivity.this;
                String message = "Search clicked";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                return true;

            case R.id.action_save:
                context = ItineraryActivity.this;
                message = "Save clicked";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                return true;

            case R.id.action_settings:
                context = ItineraryActivity.this;
                Intent startSettingsActivity = new Intent(context, SettingsActivity.class);
                startActivity(startSettingsActivity);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
