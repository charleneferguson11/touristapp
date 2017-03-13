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
    private Double mLongitude;
    private Double mLatitude;
    private ArrayList<LatLng> mLatLngArrayList;
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

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            mLatitude = extras.getDouble("latitude");
            mLongitude = extras.getDouble("longitude");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        try {
            getGeoCoordinates();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * This method creates a latitude and longitude object
    * Updates the camera on the map
    * This method positions the camera on the map using latitude,
    * longitude and the zoom level of the camera.
    * @param latitude is coordinate for latitude
    * @param longitude is coordinate for longitude
    * @param zoom  is the zoom level for the camera
    * */

    public void addMapLocation(double latitude, double longitude, float zoom) {
        // Create a latlng object
        LatLng aLocation = new LatLng(latitude, longitude);
        //
        CameraUpdate updateMapCamera = CameraUpdateFactory.newLatLngZoom(aLocation, zoom);
        // Sets the initial state of the camera
        mMap.moveCamera(updateMapCamera);
    }

    /*
    * This method adds a custom marker to the map.
    * @param address: Address object from geocoder api
    * @param lat: Latitude
    * @param lng: Longitude
    *
    * */
    public void addCustomMarker(Address address, double lat, double lng) {
        MarkerOptions options = new MarkerOptions()
                .title(address.getLocality()).position(new LatLng(lat,lng))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        // Add country to info window if available.
        String country = address.getCountryName();
        if(country.length() > 0){
            options.snippet(country);
        }

        mMarker = mMap.addMarker(options);

    }

    /*
    * Implement geocoder api to obtain an Address from a name or postcode search
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
            addMapLocation(addressLatitude, addressLongitude, 15);
            // Set up the marker's name, colour and position.
            MarkerOptions options = new MarkerOptions()
                    .title(localityName)
                    .position(new LatLng(addressLatitude, addressLongitude))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

            // Add a marker to the map
            mMarker = mMap.addMarker(options);

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


