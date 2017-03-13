package dreamteamuk.touristapp;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker mMarker;
    private double mLongitude;
    private double mLatitude;
    private float mCameraZoom;
    private LatLng mCurrentPosition;
    private Geocoder mGeocoder;
    private List<Address> mAddressArrayList;
    private ArrayList<LatLng> mLatLngArrayList;
    private Address mAddress;
    private String mFeatureName;
    private String mAddressLine;
    private String mLocality;
    private String mPostCode;
    private String mCountry;

    private int mSetNumberOfResults;

    LatLng britishMuseum = new LatLng(51.519788, -0.126976);
    LatLng nationalGallery = new LatLng(51.509485, -0.128560);
    LatLng nationalHistoryMuseum = new LatLng(51.496869, -0.176356);
    LatLng londonUk = new LatLng(51.535995, -0.129100);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Provides the mechanism for returning to the previous task.
        // Back button returns to or navigates back from this task.
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Add the Back button
        ActionBar actionbar = this.getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }

        mSetNumberOfResults = 1;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mLatitude = extras.getDouble("latitude");
            mLongitude = extras.getDouble("longitude");
        } else {
            mLatitude = 51.535995;
            mLongitude = -0.129100;
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

       /* try {
            getGeoCoordinates();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        mCurrentPosition = createLatLng(mLatitude, mLongitude);

        addMapLocation(mCurrentPosition, mCameraZoom = 15);

        // Use reversegeocoding to get details for the marker window
        try {
            getGeoAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }


        getAddressDetails();

        //Add customer markers of current position
        addCustomMarker(mAddress, mCurrentPosition);

    }

    public LatLng createLatLng(double latitude, double longitude) {
        return new LatLng(latitude, longitude);
    }

    /*
    * This method positions the camera on the map using latitude,
    * longitude and the zoom level of the camera.
    * @param latlng is the location object
    * @param zoom  is the zoom level for the camera
    * */

    public void addMapLocation(LatLng latLng, float zoom) {
        CameraUpdate updateMapCamera = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        // Sets the initial state of the camera
        mMap.moveCamera(updateMapCamera);
    }


    public void addMapLocation2(double latitude, double longitude, float zoom) {
        CameraUpdate updateMapCamera = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), zoom);
        // Sets the initial state of the camera
        mMap.moveCamera(updateMapCamera);
    }

    /*
    * This method adds a custom marker to the map.
    * @param address: Address object from geocoder api
    * @param latlng: Latlng object
    * */
    public void addCustomMarker(Address address, LatLng latLng) {

        MarkerOptions options = new MarkerOptions()
                .title(address.getLocality()).position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        // Add country to info window if available.

        if (mCountry.length() > 0) {
            options.snippet(mCountry);
        }

        // Add feature name to info window if available.
        if (mFeatureName.length() > 0) {
            options.snippet(mFeatureName);
        }

        // Add marker options to map.
        mMarker = mMap.addMarker(options);
    }

    /*
    * Get Address details from latitude and longitude
    * */
    public void getGeoAddress() throws IOException {
        mGeocoder = new Geocoder(this);
        mAddressArrayList = mGeocoder.getFromLocation(mLatitude, mLongitude, mSetNumberOfResults);
    }

    /*
    * Implements geocoder api to obtain an Address from a name or postcode search
    * Once we have the address object we obtain the name and latitude and longitude
    * coordinates.
    * */
    public void getGeoCoordinates() throws IOException {

        String searchString = "London";

        Geocoder gc = new Geocoder(this);
        //Get a list of address objects based on the search string and set number of results to 1
        List<Address> list = gc.getFromLocationName(searchString, 1);

        if (list.size() > 0) {
            Address address = list.get(0);
            String localityName = address.getLocality();
            double addressLatitude = address.getLatitude();
            double addressLongitude = address.getLongitude();
            // Call the method that positions the camera on the map
            addMapLocation2(addressLatitude, addressLongitude, 15);
            // Set up the marker's name, colour and position.
            MarkerOptions options = new MarkerOptions()
                    .title(localityName)
                    .position(new LatLng(addressLatitude, addressLongitude))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

            // Add a marker to the map
            mMarker = mMap.addMarker(options);

        }

    }

    /*This method get the address details from the address object*/
    public void getAddressDetails() {
        if (mAddressArrayList.size() > 0) {
            mAddress = mAddressArrayList.get(0);
            mFeatureName = mAddress.getFeatureName();
            mAddressLine = mAddress.getAddressLine(0);
            mLocality = mAddress.getLocality();
            mPostCode = mAddress.getPostalCode();
            mCountry = mAddress.getCountryName();
        }
    }


    /**
     * Create an options menu
     *
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map, menu);

        return true;
    }


}


