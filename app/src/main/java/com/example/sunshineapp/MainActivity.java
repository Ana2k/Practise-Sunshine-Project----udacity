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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//BEFORE PROCEEDING.
//You had to complete the todos in the toy app
//creating the menus


public class MainActivity extends AppCompatActivity implements GreenAdapter.ListItemClickListener{

    private static final int NUM_LIST_ITEMS = 100;
    private Toast mToast;

    /*References to RecyclerView and Adapter to reset the list
    when the reset menu item is clicked.
     */
    private GreenAdapter mAdapter;
    private RecyclerView mNumberList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //if id is action_refresh setup new adapter setup call
        int actionRefreshId = R.id.action_refresh;
        if(item.getItemId()==actionRefreshId){
            //setup new adapter from RecyclerView
            mAdapter = new GreenAdapter(NUM_LIST_ITEMS, this);
            mNumberList.setAdapter(mAdapter);
        }
        return super.onOptionsItemSelected(item);
    }

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

        mAdapter = new GreenAdapter(NUM_LIST_ITEMS, this);
        mNumberList.setAdapter(mAdapter);

    }


    @Override
    public void onListItemClickListener(int clickedItemIndex) {
        //show a toast when an item was clicked? how?
        if(mToast!=null){
            //HOW DO I CANCEL THE TOAST?
            //simple just mToast.cancel
            mToast.cancel();
        }

        String msg = "Item #"+clickedItemIndex+"clicked";
        mToast = Toast.makeText(this,msg,Toast.LENGTH_SHORT);
        mToast.show();
        //like cancel is a standalone value is added to mToast instead

        //way of writing a Toast.makeText(this,toastMessage,Toast.LENGTH_LONG);


    }
}
//https://github.com/Ana2k/Practise-Sunshine-Project----udactiy/blob/toy_app_network/app/src/main/java/com/example/sunshineapp/MainActivity.java
//The toy app AsyncTask :)
//https://github.com/udacity/ud851-Sunshine/blob/S02.01-Exercise-Networking/app/src/main/java/com/example/android/sunshine/MainActivity.java
//The todos
//MAIN ACTIVITY
// TODO (8) Implement GreenAdapter.ListItemClickListener from the MainActivity
// TODO (9) Create a Toast variable called mToast to store the current Toast
// TODO (13) Pass in this as the ListItemClickListener to the GreenAdapter constructor
// TODO (10) Override ListItemClickListener's onListItemClick method
// TODO (11) In the beginning of the method, cancel the Toast if it isn't null
// TODO (12) Show a Toast when an item is clicked, displaying that item number that was clicked
// TODO (14) Pass in this as the ListItemClickListener to the GreenAdapter constructor

