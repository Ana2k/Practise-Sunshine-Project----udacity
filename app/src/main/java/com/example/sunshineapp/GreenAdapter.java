package com.example.sunshineapp;

import android.content.Context;
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

    /**
     * Constructor for GreenAdapter that accepts a number of items to display and the specifications f
     * for the ListItemClickListener
     *
     * @param numberOfItems
     */
    public GreenAdapter(int numberOfItems){
        viewHolderCount=0;
        mNumberItems = numberOfItems;
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
    public class NumberViewHolder extends RecyclerView.ViewHolder {
        private TextView listItemNumberView;
        private TextView viewHolderIndex;

        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
            viewHolderIndex = (TextView) itemView.findViewById(R.id.tv_view_holder_instance);

        }

        /**
         * * @param listIndex Position of the item in the list
         */
        void bind(int listIndex) {
           //a good example to know how to bind properly
            listItemNumberView.setText(String.valueOf(listIndex));

//             Be careful to get the String representation of listIndex, as using setText with an int does something different

        }
    }
}
//For Layout Manager
//https://classroom.udacity.com/courses/ud851/lessons/c81cb722-d20a-495a-83c6-6890a6142aac/concepts/1f84d82c-4b55-4dfc-969b-46bf1dfaf5e3

//    Adapter does not cache views associated with each item (that's the job of the ViewHolder class) nor does it recycle them; that’s what our RecyclerView does.
//best recycler view synopsis i have seen so far
//https://classroom.udacity.com/courses/ud851/lessons/c81cb722-d20a-495a-83c6-6890a6142aac/concepts/5432ad56-89fe-415c-8561-bca0b9a425dc


