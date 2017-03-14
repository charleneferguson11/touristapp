package dreamteamuk.touristapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by charlene on 14/03/2017.
 */

public class PlaceFragment extends Fragment {

    private Place mPlace;
    // Text that displays the place name
    private TextView mPlaceNameTextView;
    // Text that displays the place type
    private TextView mPlaceTypeTextView;
    // Text that displays the place phone
    private TextView mPlacePhoneTextView;
    // Text that displays the place web
    private TextView mPlaceWebTextView;
    // Text that displays the place price
    private TextView mPlacePriceTextView;
    // Text that displays the place opening times
    private TextView mPlaceOpenTextView;
    // Text that displays the place address
    private TextView mPlaceAddressTextView;
    // Displays the place image
    private ImageView mPlaceImage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlace = new Place();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_place, container, false);

        mPlaceNameTextView = (TextView) v.findViewById(R.id.detail_place_name);
        mPlaceAddressTextView = (TextView) v.findViewById(R.id.detail_place_address);
        mPlaceTypeTextView = (TextView) v.findViewById(R.id.detail_place_type);
        mPlaceWebTextView = (TextView) v.findViewById(R.id.detail_place_web);
        mPlacePhoneTextView = (TextView) v.findViewById(R.id.detail_place_phone);
        mPlaceOpenTextView = (TextView) v.findViewById(R.id.detail_place_open);
        mPlacePriceTextView = (TextView) v.findViewById(R.id.detail_place_price);
        mPlaceImage = (ImageView) v.findViewById(R.id.detail_place_image);

        return v;
    }
}
