package com.example.sunshineapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sunshineapp.data.SunshinePreferences;
import com.example.sunshineapp.utilities.NetworkUtils;
import com.example.sunshineapp.utilities.OpenWeatherJsonUtils;
import com.example.sunshineapp.utilities.SunshineWeatherUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
//BEFORE PROCEEDING.
//You had completed the last todo on this page
//you had to run the app once check if there were any non committed commits.
//https://classroom.udacity.com/courses/ud851/lessons/e5d74e43-743c-455e-9a70-7545a2da9783/concepts/c4aba829-2bb7-42a0-9214-42fef3534b93
//https://classroom.udacity.com/courses/ud851/lessons/e5d74e43-743c-455e-9a70-7545a2da9783/concepts/d6dd3b5a-70c1-48ab-97d9-59ef9d10062f
//rem two todos

public class MainActivity extends AppCompatActivity {
    private TextView mWeatherTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);
        loadWeatherData();
    }
    void loadWeatherData(){
        String location = SunshinePreferences.getPreferredWeatherLocation(getBaseContext());

        new LocationQueryTask().execute(location);
        //foramtted for further lessons where we take user input presumably
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_refresh,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuSelected = item.getItemId();
        if(idMenuSelected == R.id.action_refresh){
            mWeatherTextView.setText("");
            loadWeatherData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class LocationQueryTask extends AsyncTask<String,Void, String[]> {

        @Override
        protected String[] doInBackground(String... parameters) {
            //first we check wether parameter has any value
            if (parameters.length==0){
                //no data has been passed.
                Log.d("Main - doInBackground","PARAM LENGTH IS NULL");
                return null;
            }
            /* If there's no zip code, there's nothing to look up. */
            String location = parameters[0];
            URL weatherRequestURL = NetworkUtils.buildURL(location);

            try{
                String weatherSearchResponse = NetworkUtils.getResponseHttpUrl(weatherRequestURL);
                String[] simpleJsonWeatherData = OpenWeatherJsonUtils.getSimpleWeatherStringsfromJson(getApplicationContext(),weatherSearchResponse);
                return simpleJsonWeatherData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] weatherResponses) {
            if(weatherResponses != null){
                /*
                 * Iterate through the array and append the Strings to the TextView. The reason why we add
                 * the "\n\n\n" after the String is to give visual separation between each String in the
                 * TextView. Later, we'll learn about a better way to display lists of data.
                 */

                for(String weatherResponse : weatherResponses){
                mWeatherTextView.append(weatherResponse+"\n\n\n");
                }
            }
        }
    }
}
//https://github.com/Ana2k/Practise-Sunshine-Project----udactiy/blob/toy_app_network/app/src/main/java/com/example/sunshineapp/MainActivity.java
//The toy app AsyncTask :)
//https://github.com/udacity/ud851-Sunshine/blob/S02.01-Exercise-Networking/app/src/main/java/com/example/android/sunshine/MainActivity.java
//The todos