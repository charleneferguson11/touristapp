package dreamteamuk.touristapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ItineraryActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_itinerary);

        // Add button to add Itinerary items
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


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
