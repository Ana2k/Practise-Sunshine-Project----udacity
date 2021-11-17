package com.example.sunshineapp;

import android.content.Context;
import android.location.GpsStatus;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * We couldn't come up with a good name for this class. Then, we realized
 * that this lesson is about RecyclerView.
 *
 * RecyclerView... Recycling... Saving the planet? Being green? Anyone?
 * #crickets
 *
 * Avoid unnecessary garbage collection by using RecyclerView and ViewHolders.
 *
 * If you don't like our puns, we named this Adapter GreenAdapter because its
 * contents are green.
 */
public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.NumberViewHolder> {

    private static final String TAG = GreenAdapter.class.getSimpleName();
//    almost the only way i know how to make tags
    private int mNumberItems;
    private static int viewHolderCount;

    //how to add an interface here?
    public interface ListItemClickListener{
        void onListItemClickListener(int parameter);
        //rename this parameter.
    }
    private ListItemClickListener mOnClickListener;

    /**
     * Constructor for GreenAdapter that accepts a number of items to display and the specifications f
     * for the ListItemClickListener
     *
     * @param numberOfItems
     */
    public GreenAdapter(int numberOfItems,ListItemClickListener listener){
        viewHolderCount=0;
        mNumberItems = numberOfItems;
        mOnClickListener = listener;
        //was putting this wrongly in the NumberViewHolder
    }




    /**
     *
     * for each new vieholder created enough viewholders to fill the screen andn allow for screen
     *
     * @param viewGroup - viewGroup in which these viewHolders are contained in
     * @param viewType - If your RecyclerView has more than one type of item (which ours doesn't) you
     *      *                  can use this viewType integer to provide a different layout. See
     *      *                  for more details.
     * @return new NumberViewHolder that holds the View for each list item
     */
    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int itemLayout = R.layout.number_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        //Now I know why view groups inflated have false or true as answer

        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(itemLayout,viewGroup,shouldAttachToParentImmediately);

        NumberViewHolder viewHolder = new NumberViewHolder(view);

        viewHolder.viewHolderIndex.setText("ViewHolder index: "+(viewHolderCount));
        int backgroundColorForViewHolder = ColorUtils.getViewHolderBackgroundFromInstance(context,viewHolderCount);
        viewHolder.itemView.setBackgroundColor(backgroundColorForViewHolder);
        viewHolderCount+=1;

        Log.d(TAG,"onCreateViewHolder: number of viewHolders created: "+viewHolderCount);


        return viewHolder;
    }

    /**
     * binds views together
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    /**
     * Cache of children views for a list item
     */
    public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        HOW TO IMPLEMENT A CLICK LISTENER here?
        private TextView listItemNumberView;
        private TextView viewHolderIndex;

        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
            viewHolderIndex = (TextView) itemView.findViewById(R.id.tv_view_holder_instance);
            itemView.setOnClickListener(this);
        }

        /**
         * * @param listIndex Position of the item in the list
         */
        void bind(int listIndex) {
           //a good example to know how to bind properly
            listItemNumberView.setText(String.valueOf(listIndex));

//             Be careful to get the String representation of listIndex, as using setText with an int does something different

        }

        // COMPLETED (6) Override onClick, passing the clicked item's position (getAdapterPosition()) to mOnClickListener via its onListItemClick method
        /**
         * Called whenever a user clicks on an item in the list.
         * @param v The View that was clicked
         */
        //thhis i could not do so looking -- i did right...
        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClickListener(getBindingAdapterPosition());
            //can check out this post.
//            https://stackoverflow.com/questions/63068519/getadapterposition-is-deprecated
        }
    }
}
//For Layout Manager
//https://classroom.udacity.com/courses/ud851/lessons/c81cb722-d20a-495a-83c6-6890a6142aac/concepts/1f84d82c-4b55-4dfc-969b-46bf1dfaf5e3

//    Adapter does not cache views associated with each item (that's the job of the ViewHolder class) nor does it recycle them; that’s what our RecyclerView does.
//best recycler view synopsis i have seen so far
//https://classroom.udacity.com/courses/ud851/lessons/c81cb722-d20a-495a-83c6-6890a6142aac/concepts/5432ad56-89fe-415c-8561-bca0b9a425dc
//IN GREENADAPTER

// TODO (1) Add an interface called ListItemClickListener
// TODO (2) Within that interface, define a void method called onListItemClick that takes an int as a parameter
// TODO (3) Create a final private ListItemClickListener called mOnClickListener
// TODO (5) Implement OnClickListener in the NumberViewHolder class
// TODO (7) Call setOnClickListener on the View passed into the constructor (use 'this' as the OnClickListener)

// TODO (6) Override onClick, passing the clicked item's position (getAdapterPosition()) to mOnClickListener via its onListItemClick method

// TODO (4) Add a ListItemClickListener as a parameter to the constructor and store it in mOnClickListener


