package dreamteamuk.touristapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model of Tourist Place data
 */

public class TouristPlace implements Parcelable {


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
    private String mTouristPlaceName;
    private String mTouristPlaceId;
    private String mTouristId;
    private String mTouristRating;
    private double mLatitude;
    private double mLongitude;

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public String getPhotoRef() {
        return mPhotoRef;
    }

    public void setPhotoRef(String photoRef) {
        mPhotoRef = photoRef;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    private int mHeight;
    private String mPhotoRef;
    private int mWidth;

    public TouristPlace() {
    }


    protected TouristPlace(Parcel in) {
        this.mTouristPlaceName = in.readString();
        this.mTouristPlaceId = in.readString();
        this.mTouristId = in.readString();
        this.mLatitude = in.readDouble();
        this.mLongitude = in.readDouble();
        this.mHeight = in.readInt();
        this.mPhotoRef = in.readString();
        this.mWidth = in.readInt();
    }

    public String getTouristRating() {
        return mTouristRating;
    }

    public void setTouristRating(String touristRating) {
        mTouristRating = touristRating;
    }

    public String getTouristPlaceName() {
        return mTouristPlaceName;
    }

    public void setTouristPlaceName(String placeName) {
        mTouristPlaceName = placeName;
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

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
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
        dest.writeDouble(this.mLatitude);
        dest.writeDouble(this.mLongitude);
        dest.writeInt(this.mHeight);
        dest.writeString(this.mPhotoRef);
        dest.writeInt(this.mWidth);
    }


}










