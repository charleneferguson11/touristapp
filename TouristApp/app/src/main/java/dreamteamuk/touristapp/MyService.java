package dreamteamuk.touristapp;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;

import dreamteamuk.touristapp.utils.HttpHelper;
/*
* Source: https://gist.github.com/davidgassner/71734d7ab5703cbd34c094de5add7819
*
* */

public class MyService extends IntentService {

    public static final String TAGSERVICE = "MyService";

    public static final String SERVICE_MESSAGE = "myMessage";
    public static final String SERVICE_MESSAGE_PAYLOAD = "myMessagePayload";

    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        // Get the uri from the activity
        Uri uri = intent.getData();

        // Test that I received the data by tracing in logcat.
        Log.i(TAGSERVICE, "onHandleIntent" + uri.toString());

        String response;
        try {
            response = HttpHelper.downloadUrl(uri.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // This creates a message that can be received and handled by other components within the app.
        Intent messageIntent = new Intent(SERVICE_MESSAGE);
        messageIntent.putExtra(SERVICE_MESSAGE_PAYLOAD, response);
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getApplicationContext());
        manager.sendBroadcast(messageIntent);

    }


        /*
        * Trace lifecycle methods
        * */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAGSERVICE, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAGSERVICE, "onDestroy");
    }
}
