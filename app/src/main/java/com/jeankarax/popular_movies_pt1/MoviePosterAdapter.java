package com.jeankarax.popular_movies_pt1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jeankarax.popular_movies_pt1.Model.MovieData;

import java.util.List;

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.PosterViewHolder>{

    private Context mCtx;
    private List<MovieData> mMovieList;
    private LayoutInflater inflater;

    public MoviePosterAdapter(Context ctx, List<MovieData> movieList){
        mCtx = ctx;
        mMovieList = movieList;
    }

    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PosterViewHolder holder, int position) {

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
