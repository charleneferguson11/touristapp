package dreamteamuk.touristapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResult;
import com.google.android.gms.location.places.Places;

import java.util.List;

import dreamteamuk.touristapp.utils.NetworkHelper;

/*
* This class starts a service and retrieves the message from the service via a Broadcast receiver
*
*
*
* */

public class ServiceActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "ServiceActivity";


    //   private static final String JSON_URL = "http://api.openweathermap.org/data/2.5/forecast?q=London,us&mode=xml&APPID=";

    private static String JSON_URL2 = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=51.5085300,-0.1257400&radius=500&type=museum&key=AIzaSyCu7AOcOR8d6pekRk9_MZMPpt7j9h2hes0";

    //  private static String JSON_URL3 = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=51.5085300,-0.1257400&radius=500&type=park&key=";

    TextView mOutputTouristPlaceInfo;
    List<TouristPlace> mListOfPlaces;
    ServiceAdapter myAdapter;
    RecyclerView mRecyclerView;
    private boolean mNetworkAvailable;
    // Reference to Google API
    private GoogleApiClient mGoogleApiClient;

    // Displays the place image
    private ImageView mServicePlaceImage;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String message = intent.getStringExtra(MyService.SERVICE_MESSAGE_PAYLOAD);

            TouristPlaceParser transformResponseString = new TouristPlaceParser();
            mListOfPlaces = transformResponseString.parseMessage(message);

            Toast.makeText(ServiceActivity.this, "Places is:" + mListOfPlaces.size(), Toast.LENGTH_SHORT).show();
            displayPlaceItems(null);

            /*for (TouristPlace element :mListOfPlaces) {
                mOutputTouristPlaceInfo.append(element.getTouristPlaceName() + "\n");
            }*/
        }


    };
    private ResultCallback<PlacePhotoResult> mDisplayPhotoResultCallback
            = new ResultCallback<PlacePhotoResult>() {
        @Override
        public void onResult(PlacePhotoResult placePhotoResult) {
            if (!placePhotoResult.getStatus().isSuccess()) {
                return;
            }
            mServicePlaceImage.setImageBitmap(placePhotoResult.getBitmap());
        }
    };

    /**
     * Create an options menu
     *
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.service, menu);
        return true;
    }

    /**
     * List menu options in the action bar
     *
     * @param item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuItemSelected = item.getItemId();

        switch (item.getItemId()) {


            case R.id.action_service_settings:
                Context context = ServiceActivity.this;
                Intent startSettingsActivity = new Intent(context, SettingsActivity.class);
                startActivity(startSettingsActivity);
                return true;

            case R.id.action_places:
                context = ServiceActivity.this;
                Intent startPlaceActivity = new Intent(context, PlaceActivity.class);
                startActivity(startPlaceActivity);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addOnConnectionFailedListener(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        mServicePlaceImage = (ImageView) findViewById(R.id.place_image);


        mRecyclerView = (RecyclerView) findViewById(R.id.rv_place);


        mNetworkAvailable = NetworkHelper.hasNetworkStatusAvailability(this);
        //mOutputTouristPlaceInfo.append("network ok" + mNetworkAvailable);

        if (mNetworkAvailable) {
            Intent intent = new Intent(this, MyService.class);
            intent.setData(Uri.parse(JSON_URL2));
            startService(intent);
        } else {
            Toast.makeText(this, "Network not available", Toast.LENGTH_SHORT).show();
        }


        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(MyService.SERVICE_MESSAGE));

    }

    public void displayPlaceItems(String item) {

        if (mListOfPlaces != null) {

            myAdapter = new ServiceAdapter(this, mListOfPlaces);
            mRecyclerView.setAdapter(myAdapter);


        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(mBroadcastReceiver);
    }

    /**
     * Load a bitmap from the photos API asynchronously
     * by using buffers and result callbacks.
     */
    private void placePhotosAsync(String placeId) {
        Places.GeoDataApi.getPlacePhotos(mGoogleApiClient, placeId)
                .setResultCallback(new ResultCallback<PlacePhotoMetadataResult>() {

                    @Override
                    public void onResult(PlacePhotoMetadataResult photos) {
                        if (!photos.getStatus().isSuccess()) {
                            return;
                        }

                        PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                        if (photoMetadataBuffer.getCount() > 0) {
                            // Display the first bitmap in an ImageView in the size of the view
                            photoMetadataBuffer.get(0)
                                    .getScaledPhoto(mGoogleApiClient, mServicePlaceImage.getWidth(),
                                            mServicePlaceImage.getHeight())
                                    .setResultCallback(mDisplayPhotoResultCallback);
                        }
                        photoMetadataBuffer.release();
                    }
                });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
