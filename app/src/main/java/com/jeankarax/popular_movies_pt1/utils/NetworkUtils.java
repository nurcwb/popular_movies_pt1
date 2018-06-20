package com.jeankarax.popular_movies_pt1.utils;

import android.content.res.Resources;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jeankarax.popular_movies_pt1.R;
import com.jeankarax.popular_movies_pt1.model.MovieData;

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

    private final static String THE_MOVIEDB_BASE_URL = "http://api.themoviedb.org/3/";
    private final static String SECTION_POPULAR = "get-popular-movies";
    private final static String SECTION_TOP_RATED = "get-top-rated-movies";
    private final static String QUERY_PARAM = "api_key";
    private final static String MY_API_KEY ="YOUR MOVIEDB API KEY HERE";

    public static URL buildURL(String sectionSelected){
        String section = null;
        switch (sectionSelected){
            case "popular":
                section = SECTION_POPULAR;
                break;
            case "top rated":
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

    public static MovieData getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                MovieData movieDataResponse;
                movieDataResponse = NetworkUtils.jsonToMovieData(scanner.next());
                return movieDataResponse;
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static MovieData jsonToMovieData(String jsonResponse){
        MovieData movieDataResponse;
        GsonBuilder builder = new GsonBuilder();

        Gson gson = builder.create();
        movieDataResponse = gson.fromJson(jsonResponse, MovieData.class);

        return movieDataResponse;
    }

}
