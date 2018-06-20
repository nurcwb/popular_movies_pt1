package com.jeankarax.popular_movies_pt1.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeankarax.popular_movies_pt1.R;

public class MovieInformationsActivity extends AppCompatActivity {

    private ImageView ivMoviePoster;
    private TextView tvOriginalTitle;
    private TextView tvPlotSynopsis;
    private TextView tvUserRating;
    private TextView tvReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_informations);

        ivMoviePoster = (ImageView) findViewById(R.id.iv_movie_poster);
        tvOriginalTitle = (TextView) findViewById(R.id.tv_original_title);
        tvPlotSynopsis = (TextView) findViewById(R.id.tv_plot_synopsis);
        tvUserRating = (TextView) findViewById(R.id.tv_user_rating);
        tvReleaseDate = (TextView) findViewById(R.id.tv_release_date);

        Intent intentThatStartedThisActivity = getIntent();

    }
}
