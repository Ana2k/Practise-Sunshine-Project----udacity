package com.example.sunshineapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

//so we have a clicklistener here in forecast adapter
//and we are sending it to viewholder to extract the
//binding adapter position
//then

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {
    private String[] mWeatherData;
    private final ForecastAdapterOnClickHandler mClickHandler;

    public ForecastAdapter(ForecastAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public interface ForecastAdapterOnClickHandler{
        void OnClik(String weatherForDay);
    }

    public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mWeatherTextView ;
        public ForecastAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mWeatherTextView = (TextView) itemView.findViewById(R.id.tv_weather_data);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String weatherForDay = mWeatherData[adapterPosition];
            mClickHandler.OnClik(weatherForDay);

        }

        void bind(String weatherForThisDay){
            mWeatherTextView.setText(weatherForThisDay);
        }
    }

    @NonNull
    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutIdForListItem = R.layout.forecast_item_list;
        boolean shouldAttachToParents = false;

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttachToParents);
        return new ForecastAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapterViewHolder holder, int position) {
        String weatherForThisDay = mWeatherData[position];
        holder.bind(weatherForThisDay);
        //change made from original code.
    }


    @Override
    public int getItemCount() {
        if (null == mWeatherData) return 0;
        return mWeatherData.length;
    }
    public void setWeatherData(String[] weatherData){
        mWeatherData = weatherData;
        notifyDataSetChanged();
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


//    private String[] mWeatherData;
//    private final ForecastAdapterOnClickHandler mClickHandler;
//
////add an interface in handling clicks in adapter
//public interface ForecastAdapterOnClickHandler {
//    void onClick(String weatherForDay);
//}
//    //constructor
//    public ForecastAdapter(ForecastAdapterOnClickHandler clickHandler){
//        mClickHandler = clickHandler;
//    }
//
//
//public static class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
//{
//    private final TextView mWeatherTextView;
//
//    //How to know what variables to attach
//    //check the list layout file you are attaching the views
//    public ForecastAdapterViewHolder(@NonNull View itemView) {
//        super(itemView);
//        mWeatherTextView = (TextView) itemView.findViewById(R.id.tv_weather_data);
//        itemView.setOnClickListener(this);
//
//    }
//
//    //bind -- binds data to position in list
//    void bind(String weatherForThisDay){
//        mWeatherTextView.setText(weatherForThisDay);
//    }
//
//    @Override
//    public void onClick(View v) {
//        int adapterPosition = getAdapterPosition();
//        String weatherForDay = mWeatherData[adapterPosition];
//        mClickHandler.onClick(weatherForDay);
//    }
//    //youo dont have a proper constructor established here
//
//}
//
//
//    @NonNull
//    @Override
//    public ForecastAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
//        Context context = parentViewGroup.getContext();
//        int layoutIdForListItem = R.layout.forecast_item_list;
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        boolean shouldAttachToParentImmediately = false;
//        View view = inflater.inflate(layoutIdForListItem,parentViewGroup, shouldAttachToParentImmediately);
//
//        ForecastAdapterViewHolder viewholder = new ForecastAdapterViewHolder(view);
//        return viewholder;
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ForecastAdapterViewHolder holder, int position) {
//        //Set the text of the TextView to the weather for this list item's position
//        String weatherForThisDay = mWeatherData[position];
//        //THIS WAS TOUGH TO IMAGINE.
//
//        holder.bind(weatherForThisDay);
//    }
//
//    @Override
//    public int getItemCount() {
//        if(mWeatherData==null){
//            return 0;
//        }
//        return mWeatherData.length;
//    }
//
//    public void setWeatherData(String[] weatherData) {
//        this.mWeatherData = weatherData;
//        notifyDataSetChanged();
//    }
//
////Adapter -- connects
////Recycler View -- recycles
////ViewHolder -- caches and reuses
