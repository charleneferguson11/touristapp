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

import dreamteamuk.touristapp.utils.NetworkHelper;

public class ServiceActivity extends AppCompatActivity {

    private static final String JSON_URL = "http://api.openweathermap.org/data/2.5/forecast?q=London,us&mode=xml&APPID=b05ede3127112481a29234f10c29e71a";

    TextView mOutputWeatherInfo;
    private boolean mNetworkAvailable;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String message = intent.getStringExtra(MyService.SERVICE_MESSAGE_PAYLOAD);
            mOutputWeatherInfo.append(message + "\n");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        mOutputWeatherInfo = (TextView) findViewById(R.id.weather_output);

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mBroadcastReceiver, new IntentFilter(MyService.SERVICE_MESSAGE));

        mNetworkAvailable = NetworkHelper.hasNetworkStatusAvailability(this);
        mOutputWeatherInfo.append("network ok" + mNetworkAvailable);
    }

    /*
* Starts the service
* */
    public void runClickHandler(View view) {

        if (mNetworkAvailable) {
            Intent intent = new Intent(this, MyService.class);
            intent.setData(Uri.parse(JSON_URL));
            startService(intent);
        }else{
            Toast.makeText(this, "Network not available", Toast.LENGTH_SHORT).show();
        }

    }

    public void clearClickHandler(View view) {

        mOutputWeatherInfo.setText("");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mBroadcastReceiver);
    }
}
