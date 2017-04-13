package dreamteamuk.touristapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model of Tourist Place data
 */

public class TouristPlace implements Parcelable {


    private String mTouristPlaceName;
    private String mTouristPlaceId;
    private String mTouristId;





    public void setTouristPlaceName(String placeName) {
        mTouristPlaceName = placeName;
    }

    public String getTouristPlaceName() {
        return mTouristPlaceName;
    }


    public String getTouristPlaceId() {
        return mTouristPlaceId;
    }

    public void setTouristPlaceId(String placeId) {
        mTouristPlaceId = placeId;
    }


    public String getTouristId() {
        return mTouristId;
    }

    public void setTouristId(String id) {
        mTouristId = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTouristPlaceName);
        dest.writeString(this.mTouristPlaceId);
        dest.writeString(this.mTouristId);
    }

    public TouristPlace() {
    }

    protected TouristPlace(Parcel in) {
        this.mTouristPlaceName = in.readString();
        this.mTouristPlaceId = in.readString();
        this.mTouristId = in.readString();
    }

    public static final Creator<TouristPlace> CREATOR = new Creator<TouristPlace>() {
        @Override
        public TouristPlace createFromParcel(Parcel source) {
            return new TouristPlace(source);
        }

        @Override
        public TouristPlace[] newArray(int size) {
            return new TouristPlace[size];
        }
    };
}










