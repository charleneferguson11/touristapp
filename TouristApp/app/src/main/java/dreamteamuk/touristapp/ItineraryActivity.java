package dreamteamuk.touristapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

public class ItineraryActivity extends AppCompatActivity {

    private static final int NUMBER_OF_ITEMS = 50;
    private 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        // Add button to add Itinerary items
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    }
}
