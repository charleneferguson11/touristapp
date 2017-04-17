package dreamteamuk.touristapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;


public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {


    public static final String TOURIST_ID_KEY = "tourist_id_key";

    private static final String PHOTO_URL = "https://maps.googleapis.com/maps/api/place/photo?";


    // Stores the place objects
    private List<TouristPlace> mItems;
    // Stores the current activity
    private Context mContext;

    private Map<String, Bitmap> mBitmapMap;


    public ServiceAdapter(Context context, List<TouristPlace> items) {

        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public ServiceAdapter.ServiceViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {


        Context context = viewGroup.getContext();
        int layoutForEachListItem = R.layout.service_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachImmediately = false;

        View view = inflater.inflate(layoutForEachListItem, viewGroup, shouldAttachImmediately);

        ServiceViewHolder viewHolder = new ServiceViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, int position) {

        final TouristPlace mPlaceItem = mItems.get(position);
        holder.bind(mPlaceItem.getTouristPlaceName());

        String url = PHOTO_URL;
        Uri baseUri = Uri.parse(PHOTO_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("maxwidth" , String.valueOf(mPlaceItem.getWidth()));
        uriBuilder.appendQueryParameter("photoreference" , mPlaceItem.getPhotoRef());
        uriBuilder.appendQueryParameter("key" , "@string/key");



        Picasso.with(mContext).load(String.valueOf(uriBuilder)).into(holder.placeServiceImageView);



        holder.placeServiceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(mContext, "You selected" + mPlaceItem.getTouristPlaceName(), Toast.LENGTH_SHORT).show();
                String itemID = mPlaceItem.getTouristId();
                Intent intent = new Intent(mContext, PlaceDetailActivity.class);
                intent.putExtra(TOURIST_ID_KEY, mPlaceItem);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }



    class ServiceViewHolder extends RecyclerView.ViewHolder {

        // Will display the place in the list
        TextView placeServiceListView;

        // Stores the place image
        ImageView placeServiceImageView;

        // Stores the place view
        View placeServiceView;


        /**
         * Create a view holder which reduces the number of calls to findViewById
         * improving performance.
         *
         * @param itemView
         */

        public ServiceViewHolder(View itemView) {
            super(itemView);

            placeServiceListView = (TextView) itemView.findViewById(R.id.place_name_text);

            placeServiceImageView = (ImageView) itemView.findViewById(R.id.place_image);

            placeServiceView = itemView;

        }

        void bind(String placeName) {

            placeServiceListView.setText(placeName);

        }

    }
}

