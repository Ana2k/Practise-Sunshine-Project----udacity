package com.example.sunshineapp;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.droidtermsprovider.DroidTermsExampleContract;
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

public class MainActivity extends AppCompatActivity {

    private Cursor mData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new WordFetchTask().execute();
    }

    public void onButtonClick(View view) {

    }
    //from network branch of sunshine appp
    private class WordFetchTask extends AsyncTask<Void,Void, Cursor>{

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            mData = cursor;

        }

        @Override
        protected Cursor doInBackground(Void... params) {
            //write the code to access the droidTermsExample return cursor objects
            ContentResolver resolver = getContentResolver();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Cursor cursor = resolver.query(DroidTermsExampleContract.CONTENT_URI,null,null,null);
                return cursor;
            }

            return null;

        }
    }
}

//split the files after app is done??
//basically whatever changes you want to do to ui, you have to do through adapter non other way

