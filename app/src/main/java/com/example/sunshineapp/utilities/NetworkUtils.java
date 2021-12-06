package com.example.sunshineapp.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils {
    final static String GITHUB_BASE_URL =
            "https://api.github.com/search/repositories";

    final static String PARAM_QUERY = "q";

    /*
     * The sort field. One of stars, forks, or updated.
     * Default: results are sorted by best match if no field is specified.
     */
    final static String PARAM_SORT = "sort";
    final static String sortBy = "stars";

    //buildUrl
    public static URL buildUrl(String githubSearchQuery){
        Uri builtUri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY,githubSearchQuery)
                .appendQueryParameter(PARAM_SORT,githubSearchQuery)
                .build();

        URL url = null;
        try{
            url = new URL(builtUri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    public static String getResponseFromHttpUtl(URL url) throws IOException{
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            //\A - The beginning of the input
//            https://stackoverflow.com/questions/58102714/what-does-the-a-delimiter-do-in-the-following-http-request-code
            boolean hasInput = scanner.hasNext();
            if (hasInput){
                return scanner.next();
            }
            else{
                return null;
            }
        }finally {
            urlConnection.disconnect();
        }
    }
}
