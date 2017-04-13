package dreamteamuk.touristapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/*
* This class parses the JSON object tree returned from the places http request.
*
* */
public class TouristPlaceParser {

    public static List<TouristPlace> parseMessage(String content){

        try {
            JSONObject root = new JSONObject(content);
            JSONArray contentArray = root.getJSONArray("results");
            List<TouristPlace> touristPlaceList = new ArrayList<>();

            for (int i= 0; i < contentArray.length(); i++) {

                TouristPlace touristPlace = new TouristPlace();
                JSONObject results = contentArray.getJSONObject(i);
                touristPlace.setTouristId(results.getString("id"));
                touristPlace.setTouristPlaceName(results.getString("name"));
                touristPlace.setTouristPlaceId(results.getString("place_id"));
               // touristPlace.setTouristRating(results.getString("rating"));
                touristPlaceList.add(touristPlace);
            }

            return touristPlaceList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }





}
