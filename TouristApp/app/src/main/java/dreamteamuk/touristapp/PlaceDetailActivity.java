package dreamteamuk.touristapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class PlaceDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        TouristPlace myTouristPlace = getIntent().getParcelableExtra(ServiceAdapter.TOURIST_ID_KEY);

        TextView myPlaceTextView = (TextView) findViewById(R.id.place_detail_name);

        myPlaceTextView.setText(myTouristPlace.getTouristPlaceName());
        myPlaceTextView.append("\n " + myTouristPlace.getTouristPlaceId());
        myPlaceTextView.append("\n " + myTouristPlace.getLatitude());
        myPlaceTextView.append("\n " + myTouristPlace.getLongitude());
        myPlaceTextView.append("\n " + myTouristPlace.getPhotoRef());
        myPlaceTextView.append("\n " + myTouristPlace.getHeight());
        myPlaceTextView.append("\n " + myTouristPlace.getWidth());




    }
}
