package dreamteamuk.touristapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
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

public class TestPlaceActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    private PlacePicker.IntentBuilder builder;
    private int PLACE_PICKER_REQUEST = 1;
    private TextView mPlace;
    private GoogleApiClient mGoogleApiClient;
    private ImageView mImageView;



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
        setContentView(R.layout.activity_test_place);

        // Back button returns to the previous
        ActionBar actionbar = this.getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }



        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this,this)
                .build();

        mPlace = (TextView) findViewById(R.id.textView);
        mImageView = (ImageView) findViewById(R.id.image);
        mPlace.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                builder = new PlacePicker.IntentBuilder();
                Intent intent;
                try {
                    intent = builder.build(TestPlaceActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {

            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
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
                mPlace.setText(name + "\n " + address);
                mPlace.append("\n " + charPhoneNumber
                        + "\n " +webAddress );

            }
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private ResultCallback<PlacePhotoResult> mDisplayPhotoResultCallback
            = new ResultCallback<PlacePhotoResult>() {
        @Override
        public void onResult(PlacePhotoResult placePhotoResult) {
            if (!placePhotoResult.getStatus().isSuccess()) {
                return;
            }
            mImageView.setImageBitmap(placePhotoResult.getBitmap());
        }
    };

    /**
     * Load a bitmap from the photos API asynchronously
     * by using buffers and result callbacks.
     */
    private void placePhotosAsync(String placeId) {
        //final String placeId = "ChIJrTLr-GyuEmsRBfy61i59si0"; // Australian Cruise Group
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
                                    .getScaledPhoto(mGoogleApiClient, mImageView.getWidth(),
                                            mImageView.getHeight())
                                    .setResultCallback(mDisplayPhotoResultCallback);
                        }
                        photoMetadataBuffer.release();
                    }
                });
    }



}
