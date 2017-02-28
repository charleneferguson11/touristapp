package dreamteamuk.touristapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by charlene on 17/02/2017.
 * This class populates the items of the recycler view.
 */


public class ItineraryAdapter extends RecyclerView.Adapter<ItineraryAdapter.ItineraryViewHolder> {


    // Stores the cursor
    private Cursor mCursor;

    // Stores the current activity
    private Context mContext;


    public ItineraryAdapter(Cursor cursor) {

        mCursor = cursor;
    }

    @Override
    public ItineraryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {


        Context context = viewGroup.getContext();
        int layoutForEachListItem = R.layout.itinerary_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachImmediately = false;

        View view = inflater.inflate(layoutForEachListItem, viewGroup, shouldAttachImmediately);

        ItineraryViewHolder viewHolder = new ItineraryViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItineraryViewHolder holder, int position) {

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


    /*
    * Updates the UI with the new cursor data
    * */
    public void swapCursor(Cursor paramCursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = paramCursor;

        if (paramCursor != null) {
            this.notifyDataSetChanged();
        }

    }


    class ItineraryViewHolder extends RecyclerView.ViewHolder {

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

        public ItineraryViewHolder(View itemView) {
            super(itemView);

            placeItineraryListView = (TextView) itemView.findViewById(R.id.place);
            priorityItineraryListView = (TextView) itemView.findViewById(R.id.priority);
            itemIndexItineraryListView = (TextView) itemView.findViewById(R.id.item_index);

        }

        void bind(String placeName, String priority, int listIndex) {

            placeItineraryListView.setText(placeName);
            priorityItineraryListView.setText(priority);
            itemIndexItineraryListView.setText(String.valueOf(listIndex));
        }

    }

}
