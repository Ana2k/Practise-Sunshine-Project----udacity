package com.example.sunshineapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private TextView mWeatherTextView;
    ForecastAdapter.ForecastAdapterOnClickHandler mClickListener;
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
        int adapterPosition = getBindingAdapterPosition();
        //i dont think we need it?
        mClickListener.onClick();
    }
}
//ViewHolder -- caches views
// Specifically, it avoids frequent call of findViewById() during ListView scrolling
// and that will make it smooth.
// so works according to what it is named--> View -- Holder like Cup--Holder