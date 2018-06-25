package com.jeankarax.popular_movies_pt1.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jeankarax.popular_movies_pt1.R;
import com.jeankarax.popular_movies_pt1.model.MovieData;
import com.jeankarax.popular_movies_pt1.model.MovieDataResponse;
import com.jeankarax.popular_movies_pt1.ui.adapter.MoviePosterAdapter;
import com.jeankarax.popular_movies_pt1.utils.NetworkUtils;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MoviePosterAdapter.MoviePosterAdapterOnClickHandler {

    @BindView(R.id.rv_poster)
    RecyclerView mRecyclerView;

    private MoviePosterAdapter mMoviePosterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMoviePosterAdapter = new MoviePosterAdapter(this);
        mRecyclerView.setAdapter(mMoviePosterAdapter);

        loadMovieData("popular");

    }

    /**
     * This method takes "section" as a param to sort the movies list and get it from the service.
     * This param will vary accordingly to the user' selection on the menu
     * @param section
     */
    private void loadMovieData(String section){
        new FetchMoviesTask().execute(section);
    }

    /**
     * This method will override the RecyclerView item in order to handle the item click function.
     * It will call the info activity passing a MovieData serializable object as param by intent.
     * @param movieData
     */
    @Override
    public void onClick(MovieData movieData) {
        Intent intentToStartInfoActivity = new Intent(this, MovieInformationsActivity.class);
        intentToStartInfoActivity.putExtra("MOVIE_DATA", movieData);
        startActivity(intentToStartInfoActivity);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_movies_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_top_popular){
            loadMovieData("popular");
            return true;
        }
        if(id == R.id.action_top_rated){
            loadMovieData("top_rated");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
