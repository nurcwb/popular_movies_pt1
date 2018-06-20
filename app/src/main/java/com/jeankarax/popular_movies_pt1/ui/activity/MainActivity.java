package com.jeankarax.popular_movies_pt1.ui.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jeankarax.popular_movies_pt1.R;
import com.jeankarax.popular_movies_pt1.model.MovieData;
import com.jeankarax.popular_movies_pt1.utils.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, String[]>{

        @Override
        protected String[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String section = params[0];
            URL moviesRequestUrl = NetworkUtils.buildURL(section);

            try{
                MovieData mMovieData = NetworkUtils.getResponseFromHttpUrl(moviesRequestUrl);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }

            return new String[0];
        }
    }
}
