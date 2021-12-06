package com.example.sunshineapp;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sunshineapp.utilities.NetworkUtils;

import java.net.URL;
//TODO
//https://github.com/udacity/ud851-Exercises/blob/student/Lesson05b-Smarter-GitHub-Repo-Search/T05b.01-Exercise-SaveResults/app/src/main/java/com/example/android/asynctaskloader/MainActivity.java

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final String SEARCH_QUERY_URL_EXETRA = "query";
    private static final String SEARCH_RESULTS_RAW_JSON = "results";


    private TextView mSearchResultsTextView, mErrorMessageDisplay, mUrlDisplayTextView;
    private EditText mSearchboxEditText;
    private ProgressBar mLoadingIndicator;

    private final int GITHUB_SEARCH_LOADER = 13;

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

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setup vars
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mSearchboxEditText = (EditText) findViewById(R.id.et_search_box);

        //if the savedInstance is not null, set the text of searcha nd textResult textView
        if(savedInstanceState!=null ){
            String queryUrl = savedInstanceState.getString(SEARCH_QUERY_URL_EXETRA);
            String rawJsonUrl = savedInstanceState.getString(SEARCH_RESULTS_RAW_JSON);
            mUrlDisplayTextView.setText(queryUrl);
            mSearchResultsTextView.setText(rawJsonUrl);

        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String queryUrl = mUrlDisplayTextView.getText().toString();
        outState.putString(SEARCH_QUERY_URL_EXETRA,queryUrl);

        String rawJsonUrl = mSearchResultsTextView.getText().toString();
        outState.putString(SEARCH_RESULTS_RAW_JSON,rawJsonUrl);
    }

    /**
     * This method retrieves the search text from the EditText, constructs the
     * URL (using {@link NetworkUtils}) for the github repository you'd like to find, displays
     * that URL in a TextView, and finally fires off an AsyncTask to perform the GET request using
     * our {@link GithubQueryTask}
     */
    private void makeGithubSearchQuery(){
        String githubQuery = mSearchboxEditText.getText().toString();
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        mUrlDisplayTextView.setText(githubSearchUrl.toString());

        if(githubQuery.equals("") ||githubQuery==null || githubSearchUrl==null){
            return;
        }

        Bundle queryBundle = new Bundle();
        queryBundle.putString(SEARCH_QUERY_URL_EXETRA,githubSearchUrl.toString());




    }

    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            @Override
            protected void onStartLoading(){
                if(args==null){
                    return;
                }
                mLoadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();
            }
            @Override
            public String loadInBackground() {
                //takes out the data using NetworkUtils.getReponseFromHttpUrl
                String searchQueryUrlString = args.getString(SEARCH_QUERY_URL_EXETRA);
                if (TextUtils.isEmpty(searchQueryUrlString)){
                    return null;
                }

                try{
                    URL githubUrl = new URL(searchQueryUrlString);
                    String githubSearchResults = NetworkUtils.getResponseFromHttpUtl(githubUrl);
                    return githubSearchResults;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        //if data is not null show it by setting text
        // else showErrorMessage.
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if(data == null){
            showErrorMessage();
        }
        else{

            showJsonDataView();
            mSearchResultsTextView.setText(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        /*
         * We aren't using this method in our example application, but we are required to Override
         * it to implement the LoaderCallbacks<String> interface
         */
    }

    private void showErrorMessage() {
        //the error result is visible the search string gets non visible
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mSearchResultsTextView.setVisibility(View.INVISIBLE);
    }

    private void showJsonDataView() {
        //the search result is visible the error string gets non visible
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mSearchResultsTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.action_search){
            makeGithubSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
//TODO fill out the overrides and create supporting functions.

