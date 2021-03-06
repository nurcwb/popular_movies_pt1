package com.jeankarax.popular_movies_pt1.utils;

import android.app.VoiceInteractor;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jeankarax.popular_movies_pt1.BuildConfig;
import com.jeankarax.popular_movies_pt1.model.MovieDataResponse;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

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
    private final static String MY_API_KEY = BuildConfig.MY_MOVIE_DB_API_KEY;

    private  static MovieDataResponse movieDataResponse;

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
     * @deprecated
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

    /**
     * I implemented Volley lib as udacity recomendation in order to siplify the service calls.
     * Also implemented EventBus lib in order to simplify the communication between the activity and
     * the class where the call is in, instead of calling the service inside the activity.
     * @param url
     * @param ctx
     */
    public static void getMovieDataFromService(URL url, Context ctx){
        VolleySingleton volleySingleton = VolleySingleton.getInstance(ctx);
        RequestQueue queue = volleySingleton.getmRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                movieDataResponse = NetworkUtils.jsonToMovieData(response);
                EventBus.getDefault().post(movieDataResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Network error", error.getMessage());
                EventBus.getDefault().post(error);
            }
        });
        queue.add(stringRequest);
    }
}
