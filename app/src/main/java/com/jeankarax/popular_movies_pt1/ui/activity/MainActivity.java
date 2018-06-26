package com.jeankarax.popular_movies_pt1.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.jeankarax.popular_movies_pt1.R;
import com.jeankarax.popular_movies_pt1.model.MovieData;
import com.jeankarax.popular_movies_pt1.model.MovieDataResponse;
import com.jeankarax.popular_movies_pt1.ui.adapter.MoviePosterAdapter;
import com.jeankarax.popular_movies_pt1.utils.NetworkUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MoviePosterAdapter.MoviePosterAdapterOnClickHandler {

    @BindView(R.id.rv_poster)
    RecyclerView mRecyclerView;

    private MoviePosterAdapter mMoviePosterAdapter;
    private MovieDataResponse mMovieDataResponse;

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
     * Registered the activity into the BusEvent thread in order to "listen" when the EventBus posts
     * the MovieDataResponse object on it.
     */
    @Override
    protected void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);
    }

    /**
     * Unregister the activity, so it will not listen anymore when it is not showing
     */
    @Override
    protected void onStop(){
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * This method takes "section" as a param to sort the movies list and get it from the service.
     * This param will vary accordingly to the user' selection on the menu
     * @param section
     */
    private void loadMovieData(String section){
        URL moviesRequestUrl = NetworkUtils.buildURL(section);
        NetworkUtils.getMovieDataFromService(moviesRequestUrl, getApplicationContext());
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

    /**
     * Subscribed a method for when the activity gets a MovieDataResponse object from the
     * EventBus thread
     * @param movieDataResponse
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMovieDataResponse(MovieDataResponse movieDataResponse){
        mMovieDataResponse = movieDataResponse;
        mMoviePosterAdapter.setmMovieList(movieDataResponse.getResults());
    }

    /**
     * Subscribed a method for when the call gets an error object (this error can be a connection
     * error or a call error)
     * @param error
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(VolleyError error){
        Toast.makeText(this, getString(R.string.connection_error_message), Toast.LENGTH_LONG).show();
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
