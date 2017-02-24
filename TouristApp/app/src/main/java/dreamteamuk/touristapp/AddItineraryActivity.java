package dreamteamuk.touristapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class AddItineraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_itineary);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.additinerary, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_save:
                Context context = AddItineraryActivity.this;
                String message = "Save clicked";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                return true;

            case R.id.action_delete:
                context = AddItineraryActivity.this;
                message = "Delete clicked";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
