package com.example.sunshineapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder {
    private TextView mWeatherTextView;
    //How to know what variables to attach
    //check the list layout file you are attaching the views

    public ForecastAdapterViewHolder(@NonNull View itemView) {
        super(itemView);
        mWeatherTextView = (TextView) itemView.findViewById(R.id.tv_weather_data);
    }

    //bind -- binds data to position in list
    void bind(int position, String weatherForThisDay){
        mWeatherTextView.setText(String.valueOf(position));
        //TODO() weather put in this place
    }
}
//ViewHolder -- caches views
// Specifically, it avoids frequent call of findViewById() during ListView scrolling
// and that will make it smooth.
// so works according to what it is named--> View -- Holder like Cup--Holder