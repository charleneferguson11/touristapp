package dreamteamuk.touristapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ItineraryActivity extends AppCompatActivity {
    // Number of items the view can hold
    private static final int NUMBER_OF_ITEMS = 10;
    // member that holds the adapter for the itinerary list
    private ItineraryAdapter mAdapter;
    // member that holds recyclerview of the itinerary list
    private RecyclerView mItineraryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        // Add button to add Itinerary items
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        mItineraryList = (RecyclerView) findViewById(R.id.rv_itinerary);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mItineraryList.setLayoutManager(layoutManager);

        mAdapter = new ItineraryAdapter(NUMBER_OF_ITEMS);
        mItineraryList.setAdapter(mAdapter);
    }
}
