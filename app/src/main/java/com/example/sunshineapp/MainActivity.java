package com.example.sunshineapp;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
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

//--||
//--||
//--||
//-\__/
//--\/

public class MainActivity extends AppCompatActivity implements ForecastAdapter.ForecastAdapterOnClickHandler, LoaderManager.LoaderCallbacks<String[]> {


    /**
     * gets the "simple name" of the class MainActivity, which happens to be "MainActivity". One could just write
     * <p>
     * private static final String TAG = "MainActivity";
     * <p>
     * as well, but if you later decide to rename the class, y
     * ou'll have to take care to also change that string constant because
     * no compiler will tell you if you forget it.
     */
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView mErrorMessageDisplay;
    private ProgressBar mProgressBar;

    private ForecastAdapter mForecastAdapter;
    private RecyclerView mRecycleView;

    private static int LOADER_ID = 3;

    private String[] mWeatherData = null;//to be used by lambda of LoaderManager--LoaderCAllback


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecycleView = (RecyclerView) findViewById(R.id.recyclerview_forecast);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        mForecastAdapter = new ForecastAdapter(this);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setAdapter(mForecastAdapter);

        Bundle queryBundle = null;

        LoaderManager loadManager = getLoaderManager();
//        Loader<Object> searchLoader = loadManager.getLoader(LOADER_ID);
        loadManager.initLoader(LOADER_ID,queryBundle,this);
    }

    @Override
    public void OnClik(String weatherForDay) {
        Context context = MainActivity.this;
        Class destinationActivity = DetailActivity.class;
        Intent childStartActivityIntent = new Intent(context, destinationActivity);
        childStartActivityIntent.putExtra(Intent.EXTRA_TEXT, weatherForDay);
        startActivity(childStartActivityIntent);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<String[]> onCreateLoader(int id, final Bundle args) {

        return new AsyncTaskLoader<String[]>(this) {


            @Override
            protected void onStartLoading() {
                if(mWeatherData !=null){
                    deliverResult(mWeatherData);
                }
                else{
                    mProgressBar.setVisibility(View.VISIBLE);
                    forceLoad();
                }
                super.onStartLoading();
            }

            @Override
            public void deliverResult(String[] data) {
                mWeatherData = data;
                super.deliverResult(data);
            }

            @Override
            public String[] loadInBackground() {

                String locationQuery = SunshinePreferences
                        .getPreferredWeatherLocation(getBaseContext());

                URL weatherRequestURL = NetworkUtils.buildURL(locationQuery);


                try {
                    String weatherSearchResponse = NetworkUtils
                            .getResponseHttpUrl(weatherRequestURL);
                    String[] simpleJsonWeatherData = OpenWeatherJsonUtils
                            .getSimpleWeatherStringsfromJson(getApplicationContext(), weatherSearchResponse);

                    return simpleJsonWeatherData;

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            }
        };

    }//Dont know why but was not picking up mWeatherData bcs we had kept it outside of
    //the lambda function. :/??? what was that about?--> has constraints that it cant access certain classes and scopes.

    @Override
    public void onLoadFinished(Loader<String[]> loader, String[] data) {
        mProgressBar.setVisibility(View.INVISIBLE);
        mForecastAdapter.setWeatherData(data);
        if (data == null) {
            showErrorMessage();
        } else {
            showJsonDataView();

        }
    }


    @Override
    public void onLoaderReset(Loader loader) {
        /*nothing to do here
        but the app wont run without this. i think :idk:
         */
    }




    public void showJsonDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecycleView.setVisibility(View.VISIBLE);
    }

    public void showErrorMessage() {
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mRecycleView.setVisibility(View.INVISIBLE);
    }

    private void openLocationMap() {
        String addressString = "1600 Ampitheatre Parkway, CA";
        Uri geolocation = Uri.parse("geo:0,0?q=" + addressString);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geolocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(TAG, "Could not call " + geolocation.toString() + "No recieving apps maybe installed.");
        }
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
            getLoaderManager().restartLoader(LOADER_ID,null,this);
            return true;
        }
        if (idMenuSelected == R.id.action_map) {
            openLocationMap();
            return true;
        }
        if (idMenuSelected == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this,SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

//split the files after app is done??
//basically whatever changes you want to do to ui, you have to do through adapter non other way

