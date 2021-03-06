package com.jeankarax.popular_movies_pt1.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeankarax.popular_movies_pt1.R;
import com.jeankarax.popular_movies_pt1.model.MovieData;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieInformationsActivity extends AppCompatActivity {

    @BindView(R.id.iv_movie_poster)
    ImageView ivMoviePoster;
    @BindView(R.id.tv_original_title)
    TextView tvOriginalTitle;
    @BindView(R.id.tv_plot_synopsis)
    TextView tvPlotSynopsis;
    @BindView(R.id.tv_user_rating)
    TextView tvUserRating;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_informations);

        ButterKnife.bind(this);

        Intent intentThatStartedThisActivity = getIntent();
        MovieData mMovieData = (MovieData) intentThatStartedThisActivity
                .getSerializableExtra("MOVIE_DATA");

         /*
         * Here I used Picasso lib as recommended by the project guideline in order to render the
         * movie poster into the ImageView component
         */
        String mImageUrl = "http://image.tmdb.org/t/p/w500/";
        Uri movieUri = Uri.parse(mImageUrl).buildUpon()
                .appendEncodedPath(mMovieData.getPoster_path()).build();
        Picasso.with(this).load(movieUri).placeholder(R.drawable.landscape_image_svgrepo_com).error(R.drawable.error_svgrepo_com).into(ivMoviePoster);

        tvOriginalTitle.setText(getString(R.string.movie_original_title, mMovieData.getOriginal_title()));
        tvPlotSynopsis.setText(getString(R.string.movie_synopsis, mMovieData.getOverview()));
        tvUserRating.setText(getString(R.string.movie_user_rating, mMovieData.getVote_average()));

        SimpleDateFormat dtFrom = new SimpleDateFormat("yyyy-mm-dd");
        try {
            Date date = dtFrom.parse(mMovieData.getRelease_date());
            SimpleDateFormat dtFormated = new SimpleDateFormat("dd/mm/yyyy");
            tvReleaseDate.setText(getString(R.string.movie_release_date, dtFormated.format(date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
