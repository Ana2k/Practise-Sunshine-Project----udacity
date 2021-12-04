package com.example.sunshineapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//so we have a clicklistener here in forecast adapter
//and we are sending it to viewholder to extract the
//binding adapter position
//then

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {

    private String[] mWeatherData;
    private ForecastAdapterOnClickHandler mClickHandler;

    //add an interface in handling clicks in adapter
    public interface ForecastAdapterOnClickHandler {
        void onClick(String weatherForDay);
    }
    //constructor
    public ForecastAdapter(ForecastAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }


    @NonNull
    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        Context context = parentViewGroup.getContext();
        int layoutIdForListItem = R.layout.items_list;
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem,parentViewGroup, shouldAttachToParentImmediately);

        ForecastAdapterViewHolder viewholder = new ForecastAdapterViewHolder(view);
        return viewholder;

    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapterViewHolder holder, int position) {
        //Set the text of the TextView to the weather for this list item's position
        String weatherForThisDay = mWeatherData[position];
        //THIS WAS TOUGH TO IMAGINE.

        holder.bind(weatherForThisDay);
    }

    @Override
    public int getItemCount() {
        if(mWeatherData==null){
            return 0;
        }
        return mWeatherData.length;
    }

    public void setWeatherData(String[] weatherData) {
        this.mWeatherData = weatherData;
        notifyDataSetChanged();
    }

    //Adapter -- connects
    //Recycler View -- recycles
    //ViewHolder -- caches and reuses

    public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mWeatherTextView;
        private String[] mWeatherDataEach;
        private ForecastAdapter.ForecastAdapterOnClickHandler mClickListener;

        //How to know what variables to attach
        //check the list layout file you are attaching the views
        public ForecastAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mWeatherTextView = (TextView) itemView.findViewById(R.id.tv_weather_data);
            itemView.setOnClickListener(this);

        }

        //bind -- binds data to position in list
        void bind(String weatherForThisDay){
            mWeatherTextView.setText(weatherForThisDay);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String weatherForDay = mWeatherDataEach[adapterPosition];
            mClickListener.onClick(weatherForDay);
        }
    }



}

//ViewGroup -- eg. LinearLayout, RelativeLayout,FrameLayout etc.
//// Adapter does not cache views associated with each item
// (that's the job of the ViewHolder class)
// nor does it recycle them; that’s what our RecyclerView does.
//ADAPTER -- connects views
///VIEW HOLDER -- caches views
//// Specifically, it avoids frequent call of findViewById() during ListView scrolling
//// and that will make it smooth.
//// so works according to what it is named--> View -- Holder like Cup--Holder

