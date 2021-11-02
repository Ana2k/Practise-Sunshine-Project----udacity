package com.example.sunshineapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    TextView mErrorMessage;
    ProgressBar mProgressBar;
    //TODO(1) Add helper functions in MAinActivity to swap
    // the visibility of jsonDAta and the ErrorView--

    //TODO(2) CAll the error view on null or empty data
    //TODO(3) show the progress bar dutring loading hide it afterwards.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);
        mUrlDisplayTextView = (TextView) findViewById(R.id.url_display_tv);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);

        mErrorMessage = (TextView) findViewById(R.id.tv_error_message_display);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
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

    void makeGithubSearchQuery() {
        // build the URL with the text from the EditText and set the built URL to the TextView
        String githubSearchQuery = mSearchBoxEditText.getText().toString();
        URL githubSearchUrl = NetworkUtils.buildUrl(githubSearchQuery);
        mUrlDisplayTextView.setText(githubSearchUrl.toString());

        //call the HTTP request AsyncTask
        //MAIN THREAD NETWORK CALL NOT ALLOWED. so we use AsyncTask on a different Thread called

        new GithubQueryTask().execute(githubSearchUrl);
        //How to CALL A CLASS?? -----

        /**
         * More importantly this is not KOTLIN :/ so, apart from "()" usage
         * new has to be called to call the class.. bcs we need an object for it here
         */


    }
    //shows the data and hides the error msg.
    public void showJsonDataView(){
        //mErrorMessage invisbile and mSearchResults visible
        mErrorMessage.setVisibility(View.INVISIBLE);
        mSearchResultsTextView.setVisibility(View.VISIBLE);
    }

    //show the error and hide the data
    public void showErrorMessage(){
        mErrorMessage.setVisibility(View.VISIBLE);
        mSearchResultsTextView.setVisibility(View.INVISIBLE);
    }

    public class GithubQueryTask extends AsyncTask<URL, Void, String> {
        //on PreExecute override to set loading indicator to visibile

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

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
        protected void onPostExecute(String githubSearchResult) {
            mProgressBar.setVisibility(View.INVISIBLE);
            if(githubSearchResult!=null && !githubSearchResult.equals("")){
                showJsonDataView();
                mSearchResultsTextView.setText(githubSearchResult);
            }
            else{
                showErrorMessage();
//                Toast.makeText(getApplicationContext(),"NULL SENT HERE or empty response from network",Toast.LENGTH_SHORT).show();
            }
            //Hide loading indicator on complete loading
            //call show error and shhowJsonData acc to error in recieveing data or
            //success in recieving data.

        }
    }





    //github repo:
}