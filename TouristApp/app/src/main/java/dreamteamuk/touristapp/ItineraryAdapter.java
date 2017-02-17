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
 */


// Create a view holder which reduces the number of calls to findViewById improving the performance.

public class ItineraryAdapter extends RecyclerView.Adapter<ItineraryAdapter.ItineraryViewHolder> {

// Indicate how many views an adapter will hold.
    private int mNumberOfItems;
    private Cursor mCursor;
    private Context mContext;


    public ItineraryAdapter(int numberOfItems) {
        mNumberOfItems = numberOfItems;
    }

    @Override
    public ItineraryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {


        Context context = viewGroup.getContext();
        int layoutForEachListItem = R.layout.itinerary_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachImmediately = false ;

        View view = inflater.inflate(layoutForEachListItem, viewGroup, shouldAttachImmediately);

        ItineraryViewHolder viewHolder = new ItineraryViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItineraryViewHolder holder, int position) {
        holder.bind(position);

    }



    @Override
    public int getItemCount() {
        return mNumberOfItems;
    }
 class ItineraryViewHolder extends RecyclerView.ViewHolder{

     // Will display the place in the list
     TextView placeItineraryListView;
     // Will display the priority of the place
     TextView priorityItineraryListView;

     /**
      *
      * @param itemView
      */

     public ItineraryViewHolder(View itemView) {
         super(itemView);

         placeItineraryListView = (TextView) itemView.findViewById(R.id.place);
         priorityItineraryListView = (TextView) itemView.findViewById(R.id.priority);

     }

     void bind(int listIndex){
         placeItineraryListView.setText(String.valueOf(listIndex));
     }

 }



}
