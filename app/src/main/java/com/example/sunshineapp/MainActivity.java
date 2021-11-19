package com.example.sunshineapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunshineapp.data.SunshinePreferences;
import com.example.sunshineapp.utilities.NetworkUtils;
import com.example.sunshineapp.utilities.OpenWeatherJsonUtils;

import java.net.URL;
//BEFORE PROCEEDING.
//You are supposed to complete the todos.

//Wire the RecyclerView up with the Adapter and the LinearLayoutManager

public class MainActivity extends AppCompatActivity {

    private static final int NUM_LIST_ITEMS = 100;
    //needed??

    private TextView mErrorMessageDisplay;
    private ProgressBar mProgressBar;

    private ForecastAdapter mForecastAdapter;
    private RecyclerView mRecycleView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecycleView = (RecyclerView) findViewById(R.id.recyclerview_forecast);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        //LinearLayoutManager
//        by default, if you don't specify an orientation, you get a vertical list.
//                * In our case, we want a vertical list, so we don't need to pass in an orientation flag to
//                * the LinearLayoutManager constructor.
        mForecastAdapter = new ForecastAdapter();

        /*
         * LinearLayoutManager can support HORIZONTAL or VERTICAL orientations. The reverse layout
         * parameter is useful mostly for HORIZONTAL layouts that should reverse for right to left
         * languages.
         */
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setAdapter(mForecastAdapter);
//        Use setHasFixedSize(true) on mRecyclerView to designate that all items in the list will have the same size
        loadWeatherData();
    }

    void loadWeatherData() {
        //added later
        showJsonDataView();
        String location = SunshinePreferences.getPreferredWeatherLocation(getBaseContext());

        new LocationQueryTask().execute(location);
        //foramtted for further lessons where we take user input presumably
    }

    public void showJsonDataView() {
        //mErrorMessage invisbile and mSearchResults visible
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecycleView.setVisibility(View.VISIBLE);
    }

    public void showErrorMessage() {
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mRecycleView.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuSelected = item.getItemId();
        if (idMenuSelected == R.id.action_refresh) {
            mForecastAdapter.setWeatherData(null);

            loadWeatherData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class LocationQueryTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... parameters) {
            //first we check wether parameter has any value
            if (parameters.length == 0) {
                //no data has been passed.
                Log.d("Main - doInBackground", "PARAM LENGTH IS NULL");
                return null;
            }
            /* If there's no zip code, there's nothing to look up. */
            String location = parameters[0];
            URL weatherRequestURL = NetworkUtils.buildURL(location);

            try {
                String weatherSearchResponse = NetworkUtils.getResponseHttpUrl(weatherRequestURL);
                String[] simpleJsonWeatherData = OpenWeatherJsonUtils.getSimpleWeatherStringsfromJson(getApplicationContext(), weatherSearchResponse);
                return simpleJsonWeatherData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String[] weatherResponses) {
            mProgressBar.setVisibility(View.INVISIBLE);
            if (weatherResponses != null) {
                showJsonDataView();

                mForecastAdapter.setWeatherData(weatherResponses);
                //THIS IS WHERE THE LAST FUNCTION IN ADAPTER IS USED
            } else {
                showErrorMessage();
            }
        }
    }

}
//POST MAIN ACITVITY IS MORE OR LESS DONE
//split the files

//basically whatever changes you want to do to ui, you have to do through adapter non other way

//TODOs
//https://github.com/udacity/ud851-Sunshine/blob/S03.01-Exercise-RecyclerView/app/src/main/java/com/example/android/sunshine/MainActivity.java
