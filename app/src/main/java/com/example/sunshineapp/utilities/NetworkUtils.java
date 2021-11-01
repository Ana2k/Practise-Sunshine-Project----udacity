package com.example.sunshineapp.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils {
    final static String GITHUB_BASE_URL = "https://api.github.com/search/repositories";
    final static String PARAM_QUERY = "q";
    final static String PARAM_SORT = "sort";
    final static String sortBy = "stars";
    //string

    /**
     * Build Url to query Github
    @param githubSearchQuery  - is the keyword that will be queried for
    @return -- the URL to use to query the Github server
    **/
    public static URL buildUrl(String githubSearchQuery){
        //TODO() fill in the method, to build the proper Github query URL
        Uri builtUri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY,githubSearchQuery)
                .appendQueryParameter(PARAM_SORT,sortBy)
                .build();
        URL url = null;
        try{
            url = new URL(builtUri.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Funciton returns entire result from HTTP request
     * @param url HTTP response fetched from this url
     * @return --returns the contents of the HTTP response
     * @throws -- IOException related to network and stream reading
     */
    //WHAT ABOUT RETROFIT HERE -- ideally should have been a retrofit call?
    public static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);//Alt - enter did not help me here
            scanner.useDelimiter("\\A");
            // Now the scanner set as
            // delimiter the [Regexp for \A][1]
            // \A stands for :start of a string!

            // Here it returns the first(next)
            // token that is before another
            // start of string.
            // Which, I'm not sure
            // what it will be
            //https://stackoverflow.com/questions/36278304/url-scanner-delimiter-how-does-this-java-line-of-code-works
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        }finally{
            urlConnection.disconnect();
        }
        //CHANGED ONE LINE HERE USED HTTPS not http inbuilt java module...felt more apt
    }


}
