package dreamteamuk.touristapp;

import java.net.URL;
import java.util.UUID;

/**
 * Created by charlene on 09/03/2017.
 */

public class Place {
    private UUID mUUID;
    private String mPlaceName;
    private String mPlaceType;
    private String mPlaceWebUrl;
    private URL mImage;
    private String mPhone;
    private int mMaxPrice;
    private int mMinPrice;
    private int mOpen;


    public UUID getUUID() {
        return mUUID;
    }

    public String getPlaceName() {
        return mPlaceName;
    }

    public String getPlaceType() {
        return mPlaceType;
    }

    public String getPlaceWebUrl() {
        return mPlaceWebUrl;
    }

    public URL getImage() {
        return mImage;
    }

    public String getPhone() {
        return mPhone;
    }

    public int getMaxPrice() {
        return mMaxPrice;
    }

    public int getMinPrice() {
        return mMinPrice;
    }

    public int getOpen() {
        return mOpen;
    }
}
