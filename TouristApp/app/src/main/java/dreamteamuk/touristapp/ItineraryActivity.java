package dreamteamuk.touristapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ItineraryActivity extends AppCompatActivity {

    private  static final String TAG = "ItineraryActivity";
    // Number of items the view can hold
    private static final int NUMBER_OF_ITEMS = 10;
    // member that holds the adapter for the itinerary list
    private ItineraryAdapter mAdapter;
    // member that holds recyclerview of the itinerary list
    private RecyclerView mItineraryList;
    // member that holds the database
    private SQLiteDatabase mItineraryDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// Trace Activity lifecycle
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_itinerary);

        // Add button to add Itinerary items
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create an itinerary intent to start Activity that adds a new place to Itinerary
                Intent intentItinerary;
                intentItinerary = new Intent(ItineraryActivity.this, AddToItineraryActivity.class);

                // Verify that the intent will resolve to an activity
                if (intentItinerary.resolveActivity(getPackageManager()) != null) {
                    startActivity(intentItinerary);
                }

            }
        });


        mItineraryList = (RecyclerView) findViewById(R.id.rv_itinerary);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mItineraryList.setLayoutManager(layoutManager);

        // Create an instance of DbHelper to create the database and tables
        ItineraryListDbHelper dbHelper = new ItineraryListDbHelper(this);
        // Create a writable database
        mItineraryDb = dbHelper.getWritableDatabase();
        mAdapter = new ItineraryAdapter(NUMBER_OF_ITEMS);
        mItineraryList.setAdapter(mAdapter);
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

    public void addToItineraryList(View view){

    }


    //private Cursor getAllDataInTable(){

    //}


    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuItemSelected = item.getItemId();
        if(menuItemSelected == R.id.action_search){
            Context context = ItineraryActivity.this;
            String message = "Search clicked";
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }


}
