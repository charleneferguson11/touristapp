package dreamteamuk.touristapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;
import java.util.UUID;

/**
 * Model of Place data
 */

public class TouristPlace implements Parcelable{
    private UUID mUUID;
    private String mPlaceName;
    private String mPlaceType;
    private String mPlaceWebUrl;
    private URL mPlaceImage;
    private String mPhone;
    private int mMaxPrice;
    private int mMinPrice;
    private int mOpen;
    private double mLatitude;
    private double mLongitude;
    private double mAddress;

    protected TouristPlace(Parcel in) {
        mPlaceName = in.readString();
        mPlaceType = in.readString();
        mPlaceWebUrl = in.readString();
        mPhone = in.readString();
        mMaxPrice = in.readInt();
        mMinPrice = in.readInt();
        mOpen = in.readInt();
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
        mAddress = in.readDouble();
    }

    public static final Creator<TouristPlace> CREATOR = new Creator<TouristPlace>() {
        @Override
        public TouristPlace createFromParcel(Parcel in) {
            return new TouristPlace(in);
        }

        @Override
        public TouristPlace[] newArray(int size) {
            return new TouristPlace[size];
        }
    };

    public double getAddress() {
        return mAddress;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

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
        return mPlaceImage;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mPlaceName);
        parcel.writeString(mPlaceType);
        parcel.writeString(mPlaceWebUrl);
        parcel.writeString(mPhone);
        parcel.writeInt(mMaxPrice);
        parcel.writeInt(mMinPrice);
        parcel.writeInt(mOpen);
        parcel.writeDouble(mLatitude);
        parcel.writeDouble(mLongitude);
        parcel.writeDouble(mAddress);
    }
}
