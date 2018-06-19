package com.jeankarax.popular_movies_pt1.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jeankarax.popular_movies_pt1.R;
import com.jeankarax.popular_movies_pt1.model.MovieData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.PosterViewHolder>{

    private List<MovieData> mMovieList;

    private String mImageUrl = "http://image.tmdb.org/t/p/w185/";
    private Context mCtx;

    public MoviePosterAdapter(Context ctx, List<MovieData> movieList){
        mCtx = ctx;
        mMovieList = movieList;
    }

    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutIdForGridItem = R.layout.activity_movie_poster_item;
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(layoutIdForGridItem, parent, false);
        PosterViewHolder posterViewHolder = new PosterViewHolder(view);

        return posterViewHolder;
    }

    @Override
    public void onBindViewHolder(PosterViewHolder holder, int position) {
        Uri movieUri = Uri.parse(mImageUrl).buildUpon().appendEncodedPath(mMovieList.get(position)
                .getPoster_path()).build();
        Picasso.with(mCtx).load(movieUri).into(holder.ivMoviePoster);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PosterViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivMoviePoster;

        public PosterViewHolder(View itemView) {
            super(itemView);
            ivMoviePoster = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
        }
    }

}
