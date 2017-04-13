package dreamteamuk.touristapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;




public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {


    // Stores the cursor
    private Cursor mCursor;
    // Stores the current activity
    private Context mContext;


    public ServiceAdapter(Context context, Cursor cursor) {

        mCursor = cursor;
        mContext = context;

    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {


        Context context = viewGroup.getContext();
        int layoutForEachListItem = R.layout.itinerary_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachImmediately = false;

        View view = inflater.inflate(layoutForEachListItem, viewGroup, shouldAttachImmediately);

        ServiceViewHolder viewHolder = new ServiceViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, int position) {

        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String placeName = mCursor.getString(mCursor.getColumnIndex(ItineraryListContract.ItineraryListEntry.COLUMN_PLACE_NAME));
        String priority = mCursor.getString(mCursor.getColumnIndex(ItineraryListContract.ItineraryListEntry.COLUMN_PRIORITY));
        Long id = mCursor.getLong(mCursor.getColumnIndex(ItineraryListContract.ItineraryListEntry._ID));
        holder.bind(placeName, priority, position);

        //Store the id within a tag so that it is not displayed to the user
        holder.itemView.setTag(id);

    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }



    class ServiceViewHolder extends RecyclerView.ViewHolder {

        // Will display the place in the list
        TextView placeItineraryListView;
        // Will display the priority of the place
        TextView priorityItineraryListView;
        // Will display the list index
        TextView itemIndexItineraryListView;

        /**
         * Create a view holder which reduces the number of calls to findViewById
         * improving performance.
         *
         * @param itemView
         */

        public ServiceViewHolder(View itemView) {
            super(itemView);

            placeItineraryListView = (TextView) itemView.findViewById(R.id.place);
      /*      priorityItineraryListView = (TextView) itemView.findViewById(R.id.priority);
            itemIndexItineraryListView = (TextView) itemView.findViewById(R.id.item_index);*/

        }

        void bind(String placeName, String priority, int listIndex) {

            placeItineraryListView.setText(placeName);
/*            priorityItineraryListView.setText(priority);
            itemIndexItineraryListView.setText(String.valueOf(listIndex));*/
        }

    }

}

