package dreamteamuk.touristapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

public class ItineraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    }
}
