package dreamteamuk.touristapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dreamteamuk.touristapp.utils.NetworkHelper;

/*
* This class starts a service and retrieves the message from the service via a Broadcast receiver
*
*
*
* */

public class ServiceActivity extends AppCompatActivity {

    private static final String JSON_URL = "http://api.openweathermap.org/data/2.5/forecast?q=London,us&mode=xml&APPID=";

    private static String JSON_URL2 = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=51.5085300,-0.1257400&radius=500&type=museum&key=";

    private static String JSON_URL3 = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=51.5085300,-0.1257400&radius=500&type=park&key=";

    TextView mOutputTouristPlaceInfo;
    private boolean mNetworkAvailable;

    List<TouristPlace> mTouristPlaceList;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String message = intent.getStringExtra(MyService.SERVICE_MESSAGE_PAYLOAD);

            TouristPlaceParser transformResponseString = new TouristPlaceParser();
            List<TouristPlace> listOfPlaces = transformResponseString.parseMessage(message);


            //        List<TouristPlace> listOfPlaces = intent.getParcelableArrayListExtra(MyService.SERVICE_MESSAGE_PAYLOAD);
            for (TouristPlace element :listOfPlaces) {
                mOutputTouristPlaceInfo.append(element.getTouristPlaceName() + "\n");
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        mOutputTouristPlaceInfo = (TextView) findViewById(R.id.weather_output);

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mBroadcastReceiver, new IntentFilter(MyService.SERVICE_MESSAGE));

        mNetworkAvailable = NetworkHelper.hasNetworkStatusAvailability(this);
        mOutputTouristPlaceInfo.append("network ok" + mNetworkAvailable);
    }

    /*
* Starts the service
* */
    public void runClickHandler(View view) {

        if (mNetworkAvailable) {
            Intent intent = new Intent(this, MyService.class);
            intent.setData(Uri.parse(JSON_URL2));
            startService(intent);
        }else{
            Toast.makeText(this, "Network not available", Toast.LENGTH_SHORT).show();
        }

    }

    public void clearClickHandler(View view) {

        mOutputTouristPlaceInfo.setText("");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mBroadcastReceiver);
    }
}
