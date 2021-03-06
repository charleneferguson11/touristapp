package dreamteamuk.touristapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class creates the database and ensures that the schemer is updated when required.
 */

public class ItineraryListDbHelper extends SQLiteOpenHelper {

    //    Database name
    private static final String DATABASE_NAME = "itinerarylist.db";

    //    Database version
    private static final int DATABASE_VERSION = 2;


    public ItineraryListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_ITINERARY_LIST_TABLE = "CREATE TABLE " +
                ItineraryListContract.ItineraryListEntry.TABLE_ITINERARY_LIST_NAME + " (" +
                ItineraryListContract.ItineraryListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ItineraryListContract.ItineraryListEntry.COLUMN_PLACE_NAME + " TEXT NOT NULL, " +
                ItineraryListContract.ItineraryListEntry.COLUMN_PRIORITY + " TEXT NOT NULL" +
                ");";

 /*       final String SQL_CREATE_PLACE_ID_TABLE = "CREATE TABLE " +
                ItineraryListContract.ItineraryListEntry.TABLE_PLACE_ID_NAME + " (" +
                ItineraryListContract.ItineraryListEntry.COLUMN_PLACE_ID + " TEXT NOT NULL, " +
                ");";
*/

        sqLiteDatabase.execSQL(SQL_CREATE_ITINERARY_LIST_TABLE);
 //       sqLiteDatabase.execSQL(SQL_CREATE_PLACE_ID_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ItineraryListContract.ItineraryListEntry.TABLE_ITINERARY_LIST_NAME);
    //    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ItineraryListContract.ItineraryListEntry.TABLE_PLACE_ID_NAME);
        onCreate(sqLiteDatabase);

    }
}
