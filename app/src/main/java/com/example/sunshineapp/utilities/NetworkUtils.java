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
        Uri builtUri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY,githubSearchQuery)
                .appendQueryParameter(PARAM_SORT,sortBy)
                .build();
        //appendQueryParameter(String key, String value)
        //Encodes the key and value and then appends the parameter to the query string.
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
    //it is a more primitive rertrofit call only.
    public static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);//Alt - enter did not help me here
            scanner.useDelimiter("\\A");
//            https://classroom.udacity.com/courses/ud851/lessons/e5d74e43-743c-455e-9a70-7545a2da9783/concepts/195163c8-abbb-424a-90ab-2ee6d07720a3
            //explains this function best above video.
            //and some additional brownie resources. on Networking parsing etc ;)
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        }finally{
            urlConnection.disconnect();
        }
        //Anther way = OkHttp
        //CHANGED ONE LINE HERE USED HTTPS not http inbuilt java module...felt more apt
    }


}
