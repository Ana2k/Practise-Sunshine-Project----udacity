package com.example.sunshineapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
//a) why are we putting in a clickListener what is it supposed to do?
//b) what was it doing in toy_app_rv even??

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapterViewHolder> {

    private String[] mWeatherData;
    private ForecastAdapterOnClickHandler mClickHandler;
    //dont understand the root of this line yet

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
        View view = inflater.inflate(layoutIdForListItem,parentViewGroup,shouldAttachToParentImmediately);

        return new ForecastAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapterViewHolder holder, int position) {
        //Set the text of the TextView to the weather for this list item's position
        String weatherForThisDay = mWeatherData[position];
        //THIS WAS TOUG TO IMAGINE.

        holder.bind(weatherForThisDay);
    }

    @Override
    public int getItemCount() {
        if(mWeatherData==null){
            return 0;
        }
        return mWeatherData.length;
    }

    //Adapter -- connects
    //Recycler View -- recycles
    //ViewHolder -- caches and reuses

    //setWeatherData
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

