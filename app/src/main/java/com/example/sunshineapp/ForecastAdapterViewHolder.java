package com.example.sunshineapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private TextView mWeatherTextView;
    private String[] mWeatherDataEach;

    //How to know what variables to attach
    //check the list layout file you are attaching the views
    public ForecastAdapterViewHolder(@NonNull View itemView, String[] mWeatherData, View.OnClickListener mClickListener) {
        super(itemView);
        mWeatherTextView = (TextView) itemView.findViewById(R.id.tv_weather_data);
        itemView.setOnClickListener(this);
        mWeatherDataEach = mWeatherData;
        itemView.setTag(this);
        itemView.setOnClickListener(mClickListener);

    }

    //bind -- binds data to position in list
    void bind(String weatherForThisDay){
        mWeatherTextView.setText(weatherForThisDay);
    }

}
//ViewHolder -- caches views
// Specifically, it avoids frequent call of findViewById() during ListView scrolling
// and that will make it smooth.
// so works according to what it is named--> View -- Holder like Cup--Holder