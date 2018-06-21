package com.jeankarax.popular_movies_pt1.utils;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jeankarax.popular_movies_pt1.model.MovieDataResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Jean Karax.
 */

public class NetworkUtils {

    private final static String THE_MOVIEDB_BASE_URL = "http://api.themoviedb.org/3/movie/";
    private final static String SECTION_POPULAR = "popular";
    private final static String SECTION_TOP_RATED = "top_rated";
    private final static String QUERY_PARAM = "api_key";
    private final static String MY_API_KEY ="your moviedb api key here";

    /*
     *The following methods were based on the code used in the Android Development Nanodegree classes
     */

    /**
     * Builds the URL used to talk to the moviedb server using an argument to group the list by its
     * popularity or most rated, and your personal api key.
     * @param sectionSelected
     * @return The URL to use to query the moviedb service.
     */
    public static URL buildURL(String sectionSelected){
        String section = null;
        switch (sectionSelected){
            case "popular":
                section = SECTION_POPULAR;
                break;
            case "top_rated":
                section = SECTION_TOP_RATED;
                break;
            default:
                section = SECTION_POPULAR;
        }
        Uri builtUri = Uri.parse(THE_MOVIEDB_BASE_URL + section).buildUpon()
                .appendQueryParameter(QUERY_PARAM, MY_API_KEY)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * This methods takes the JSON from the service response and parse it into a MovieDataResponse
     * object to return it to the activity.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return a MovieDataResponse object populated from the JSON response
     * @throws IOException Related to network and stream reading
     */
    public static MovieDataResponse getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                MovieDataResponse movieDataResponse;
                movieDataResponse = NetworkUtils.jsonToMovieData(scanner.next());
                return movieDataResponse;
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     *I implemented GSON lib in order to simplify the JSON to object parse and mapping
     *This lib code can be found in the following repository https://github.com/google/gson
     */

    private static MovieDataResponse jsonToMovieData(String jsonResponse){
        MovieDataResponse movieDataResponse;
        GsonBuilder builder = new GsonBuilder();

        Gson gson = builder.create();
        movieDataResponse = gson.fromJson(jsonResponse, MovieDataResponse.class);

        return movieDataResponse;
    }

}
