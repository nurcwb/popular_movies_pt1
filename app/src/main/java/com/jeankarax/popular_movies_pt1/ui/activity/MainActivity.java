package com.jeankarax.popular_movies_pt1.ui.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jeankarax.popular_movies_pt1.R;
import com.jeankarax.popular_movies_pt1.model.MovieDataResponse;
import com.jeankarax.popular_movies_pt1.ui.adapter.MoviePosterAdapter;
import com.jeankarax.popular_movies_pt1.utils.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private MoviePosterAdapter mMoviePosterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_poster);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMoviePosterAdapter = new MoviePosterAdapter();
        mRecyclerView.setAdapter(mMoviePosterAdapter);

        loadMovieData("popular");

    }

    private void loadMovieData(String section){
        new FetchMoviesTask().execute(section);
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, MovieDataResponse>{

        @Override
        protected MovieDataResponse doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String section = params[0];
            URL moviesRequestUrl = NetworkUtils.buildURL(section);

            try{
                MovieDataResponse mMovieDataResponse = NetworkUtils.getResponseFromHttpUrl(moviesRequestUrl);
                return mMovieDataResponse;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(MovieDataResponse movieDataResponse){
            if(movieDataResponse.getResults() != null){
                mMoviePosterAdapter.setmMovieList(movieDataResponse.getResults());
            }
        }
    }
}
