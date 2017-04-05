package dreamteamuk.touristapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*
* Utility helper class that checks network connectivity
* */
public class NetworkHelper {

    public static boolean hasNetworkStatusAvailability(Context context){

        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);


        try {
         NetworkInfo  activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
