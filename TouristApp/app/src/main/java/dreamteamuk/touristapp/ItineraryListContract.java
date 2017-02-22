package dreamteamuk.touristapp;

import android.provider.BaseColumns;

/**
 * Created by charlene on 17/02/2017.
 * This class defines the database and tables.
 */
public class ItineraryListContract {
    public class ItineraryListEntry implements BaseColumns {
        // TABLE_NAME is itinerarylist
        public static final String TABLE_NAME = "itinerarylist";
        // COLUMN_PLACE_NAME is placeName
        public static final String COLUMN_PLACE_NAME = "placeName";
        // COLUMN_PRIORITY is priority
        public static final String COLUMN_PRIORITY = "priority";

    }
}
