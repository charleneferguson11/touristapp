package dreamteamuk.touristapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/*
* This class parses the raw JSON string returned from the places http request
* extracts the values required and creates instances of Tourist Place class.
* */
public class TouristPlaceParser {

    public static List<TouristPlace> parseMessage(String content) {

        try {
            JSONObject root = new JSONObject(content);
            JSONArray contentArray = root.getJSONArray("results");
            List<TouristPlace> touristPlaceList = new ArrayList<>();

            for (int i = 0; i < contentArray.length(); i++) {

                TouristPlace touristPlace = new TouristPlace();
                JSONObject results = contentArray.getJSONObject(i);
                JSONObject geometry = results.getJSONObject("geometry");
                JSONObject location = geometry.getJSONObject("location");
                touristPlace.setLatitude(location.getDouble("lat"));
                touristPlace.setLongitude(location.getDouble("lng"));
                touristPlace.setTouristId(results.getString("id"));
                touristPlace.setTouristPlaceName(results.getString("name"));
                JSONArray photoArray = results.optJSONArray("photos");
                if(photoArray != null) {
                JSONObject photoObject = photoArray.getJSONObject(0);
                    touristPlace.setHeight(photoObject.getInt("height"));
                    touristPlace.setPhotoRef(photoObject.getString("photo_reference"));
                    touristPlace.setWidth(photoObject.getInt("width"));
                }else{
                    touristPlace.setHeight(0);
                    touristPlace.setPhotoRef("");
                    touristPlace.setWidth(0);
                }
                touristPlace.setTouristPlaceId(results.getString("place_id"));
                touristPlace.setTouristRating(results.optString("rating"));
                touristPlaceList.add(touristPlace);
            }

            return touristPlaceList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }


}
