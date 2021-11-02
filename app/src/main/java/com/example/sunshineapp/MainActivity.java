package com.example.sunshineapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sunshineapp.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    EditText mSearchBoxEditText;
    TextView mUrlDisplayTextView, mSearchResultsTextView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    void makeGithubSearchQuery() {
        // build the URL with the text from the EditText and set the built URL to the TextView
        String githubSearchQuery = mSearchBoxEditText.getText().toString();
        URL githubSearchUrl = NetworkUtils.buildUrl(githubSearchQuery);
        mUrlDisplayTextView.setText(githubSearchUrl.toString());

        //call the HTTP request AsyncTask
        //MAIN THREAD NETWORK CALL NOT ALLOWED. so we use AsyncTask on a different Thread called

        new GithubQueryTask().execute(githubSearchUrl);

        /**
         * More importantly this is not KOTLIN :/ so, apart from "()" usage
         * new has to be called to call the class.. bcs we need an object for it here
         */

        //How to CALL A CLASS?? -----
        // Background Thread.
    }

    public static class GithubQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL streamUrl = urls[0];
            String githubSearchResponse = null;
            //You have to return the results also na...
            try {
                githubSearchResponse = NetworkUtils.getResponseFromHttpUrl(streamUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return githubSearchResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null && !s.equals("")){
                mSearchResultsTextView.setText(s);
            }
            else{
                Toast.makeText(getApplicationContext(),"NULL SENT HERE or empty response from network",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItemSelected = item.getItemId();
        if (idMenuItemSelected == R.id.action_search) {
            makeGithubSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);
        mUrlDisplayTextView = (TextView) findViewById(R.id.url_display_tv);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);
    }

    //github repo from https://classroom.udacity.com/courses/ud851/lessons/e5d74e43-743c-455e-9a70-7545a2da9783/concepts/df87e335-ec8d-4383-9e32-78f8e0f982f1
}