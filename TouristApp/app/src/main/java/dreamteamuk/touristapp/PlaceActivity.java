package dreamteamuk.touristapp;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResult;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

public class PlaceActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "PlaceActivity";


    private final int PLACE_PICKER_REQUEST = 1;

    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };

    // Identifies a location permission request
    private static final int REQUEST_LOCATION_PERMISSIONS = 12;

    // Reference to Google API
    private GoogleApiClient mGoogleApiClient;
    // Determines whether location permissions are granted
    private PlacePicker.IntentBuilder builder;
    private Place mPlace;
    // Text that displays the place data
    private TextView mPlaceDataTextView;

    // Displays the place image
    private ImageView mPlaceImage;
    private Button mPlaceButton;

    private String mPlaceData;

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (requestCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);

                //                  Place place = PlacePicker.getPlace(data, this);
                String placeID = place.getId();
                placePhotosAsync(placeID);
                String address = place.getAddress().toString();
                String name = place.getName().toString();
                String type = String.valueOf(place.getPlaceTypes());
                CharSequence charPhoneNumber = place.getPhoneNumber();
                String webAddress = String.valueOf(place.getWebsiteUri());
                //int phoneNumber = Integer.parseInt(charPhoneNumber.toString());
                /*Uri webAddress = place.getWebsiteUri();
                CharSequence charPhoneNumber = place.getPhoneNumber();
                int phoneNumber = Integer.parseInt(charPhoneNumber.toString());
*/
                mPlaceDataTextView.setText("hello" + "\n " + address);
                mPlaceDataTextView.append("\n " + charPhoneNumber
                        + "\n " + webAddress);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addOnConnectionFailedListener(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this,this)
                .build();

        mPlaceDataTextView = (TextView) findViewById(R.id.detail_place_name);
        mPlaceImage = (ImageView) findViewById(R.id.detail_place_image);
        mPlaceButton = (Button) findViewById(R.id.place_button);
        mPlaceButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                builder = new PlacePicker.IntentBuilder();
                Intent intent;
                try {
                    intent = builder.build(PlaceActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });


        // Back button returns to the previous
        ActionBar actionbar = this.getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }


    }

 /*   private void selectPlace() {
        builder = new PlacePicker.IntentBuilder();
        Intent intent;
        try {
            intent = builder.build(this);
            startActivityForResult(intent, PLACE_PICKER_REQUEST);

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

    }*/

    /*Gets the data from the place object and puts it on the UI
    * @param selectedPlace is the place object selected by the user
    * */

    public void updateUserInterface(Place currentPlace) {

        //       String placeID = currentPlace.getId();
        //      placePhotosAsync(placeID);

        mPlaceData = currentPlace.getName() + "\n";
                /*+ currentPlace.getAddress() + "\n"
                + currentPlace.getPlaceTypes() + "\n"
                + currentPlace.getWebsiteUri() + "\n"
                + currentPlace.getPhoneNumber().toString() + "\n"
                + currentPlace.getPriceLevel() + "\n";*/


        // + selectedPlace.opening_hours.periods[1].open.time + "\n";

        mPlaceDataTextView.setText(mPlaceData);

    }

    private ResultCallback<PlacePhotoResult> mDisplayPhotoResultCallback
            = new ResultCallback<PlacePhotoResult>() {
        @Override
        public void onResult(PlacePhotoResult placePhotoResult) {
            if (!placePhotoResult.getStatus().isSuccess()) {
                return;
            }
            mPlaceImage.setImageBitmap(placePhotoResult.getBitmap());
        }
    };


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
                                    .getScaledPhoto(mGoogleApiClient, mPlaceImage.getWidth(),
                                            mPlaceImage.getHeight())
                                    .setResultCallback(mDisplayPhotoResultCallback);
                        }
                        photoMetadataBuffer.release();
                    }
                });
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}// End Place Activity
