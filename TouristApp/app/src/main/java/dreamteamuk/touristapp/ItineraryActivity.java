package dreamteamuk.touristapp;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import static com.google.android.gms.location.LocationServices.API;

public class ItineraryActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private static final String TAG = "ItineraryActivity";

    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };

    // Identifies a location permission request
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;


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
    // Text that display composed
    private TextView mOutputTextView;
    // Reference to Google API
    private GoogleApiClient mGoogleApiClient;
    // Reference to Location request
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private TextView mLatitudeLabelTextView;
    private TextView mLongitudeLabelTextView;
    private TextView mLongitudeOutputTextView;
    private TextView mLatitudeOutputTextView;
    private double mCurrentLongitude;
    private double mCurrentLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Trace Activity lifecycle
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_itinerary);


        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(API)
                    .build();
        }

        // Add floating button to add Itinerary items
        //  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        // Add edit text to add data
        mNewPlaceNameEditText = (EditText) findViewById(R.id.edit_place_name);
        mNewPriorityNameEditText = (EditText) findViewById(R.id.edit_priority);

        // Add text view to display location data
        mLatitudeLabelTextView = (TextView) findViewById(R.id.latitude_label);
        mLongitudeLabelTextView = (TextView) findViewById(R.id.longitude_label);
        mLatitudeOutputTextView = (TextView) findViewById(R.id.latitude_output);
        mLongitudeOutputTextView = (TextView) findViewById(R.id.longitude_output);
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

        // Get all the records from the table
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

        // Connect the client
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
        stopLocationUpdates();
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
        //Disconnect the client
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
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

        //Convert text from input fields to strings
        String editPlacename = mNewPlaceNameEditText.getText().toString();
        String editPriority = mNewPriorityNameEditText.getText().toString();

        // Add strings to itinerary table
        addItinerary(editPlacename, editPriority);

        // Update adapter with the new data
        mAdapter.swapCursor(getAllItineraryData());

        // Clear input fields
        mNewPlaceNameEditText.getText().clear();
        mNewPriorityNameEditText.getText().clear();
    }

    /**
     * Adds a new record to Itinerary table
     *
     * @param name
     * @param priority
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
     *
     * @param id Unique id of record
     * @return boolean
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

    /**
     * Create an options menu
     *
     * @param menu
     * @return boolean
     */
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

            case R.id.action_map:
                context = ItineraryActivity.this;
                Intent startMapActivity = new Intent(context, MapActivity.class);
                startMapActivity.putExtra("latitude", mCurrentLatitude);
                startMapActivity.putExtra("longitude", mCurrentLongitude);
                startActivity(startMapActivity);
                return true;

/*            case R.id.action_places:
                context = ItineraryActivity.this;
                Intent startPlacesActivity = new Intent(context, PlacesActivity.class);
                startActivity(startPlacesActivity);
                return true;*/

        }
        return super.onOptionsItemSelected(item);
    }


    private void findLocation() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setNumUpdates(1);
        mLocationRequest.setInterval(1000);
    }

    @Override
    public void onConnected(Bundle bundle) {


        //Check if location permission is already available

        int result = ContextCompat.checkSelfPermission(this, LOCATION_PERMISSIONS[0]);
        if (result == PackageManager.PERMISSION_GRANTED) {

            findLocation();
            com.google.android.gms.location.LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            // Location permission has not been granted

            // Provide additional information to user for the use of the permission.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, LOCATION_PERMISSIONS[0])) {
                Toast.makeText(this, "Location permission is needed to get nearby places.", Toast.LENGTH_SHORT).show();
            }

            // Request location permission
            ActivityCompat.requestPermissions(this, LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSIONS);
        }


    }


    /**
     * Response to user request to grant permissions
     *
     * @param grantResults
     * @param permissions
     * @param requestCode
     **/
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                int result = ContextCompat.checkSelfPermission(this, LOCATION_PERMISSIONS[0]);
                if (result == PackageManager.PERMISSION_GRANTED) {
                    findLocation();

                } else {
                    Toast.makeText(this, "Permisison was not granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        //mOutputTextView.setText(location.toString());
        mCurrentLocation = location;
        mCurrentLatitude = location.getLatitude();
        mCurrentLongitude = location.getLongitude();
        mLatitudeOutputTextView.append(String.valueOf(mCurrentLatitude));
        mLongitudeOutputTextView.append(String.valueOf(mCurrentLongitude));
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }








/**
 *  Make an HTTP network request.
 */

/*private String makeHttpRequest() throws IOException{

    // Store the json response as a string
    String jsonResponse ="";



    return jsonResponse;
}*/


}
