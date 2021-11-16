package com.example.sunshineapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//BEFORE PROCEEDING.
//You had to complete the todos in the toy app



public class MainActivity extends AppCompatActivity {

    private static final int NUM_LIST_ITEMS = 100;

    /*References to RecyclerView and Adapter to reset the list
    when the reset menu item is clicked.
     */
    private GreenAdapter mAdapter;
    private RecyclerView mNumberList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNumberList = (RecyclerView) findViewById(R.id.rv_numbers);

        /*
         * A LinearLayoutManager is responsible for measuring and positioning item views within a
         * RecyclerView into a linear list. This means that it can produce either a horizontal or
         * vertical list depending on which parameter you pass in to the LinearLayoutManager
         * constructor. By default, if you don't specify an orientation, you get a vertical list.
         * In our case, we want a vertical list, so we don't need to pass in an orientation flag to
         * the LinearLayoutManager constructor.
         *
         * There are other LayoutManagers available to display your data in uniform grids,
         * staggered grids, and more! See the developer documentation for more details.
         */
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mNumberList.setHasFixedSize(true);


        mAdapter = new GreenAdapter(NUM_LIST_ITEMS);
        mNumberList.setAdapter(mAdapter);



    }


}
//https://github.com/Ana2k/Practise-Sunshine-Project----udactiy/blob/toy_app_network/app/src/main/java/com/example/sunshineapp/MainActivity.java
//The toy app AsyncTask :)
//https://github.com/udacity/ud851-Sunshine/blob/S02.01-Exercise-Networking/app/src/main/java/com/example/android/sunshine/MainActivity.java
//The todos