package com.jeankarax.popular_movies_pt1.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jeankarax.popular_movies_pt1.R;
import com.jeankarax.popular_movies_pt1.model.MovieData;

import java.util.List;

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.PosterViewHolder>{

    private List<MovieData> mMovieList;

    public MoviePosterAdapter(List<MovieData> movieList){
        mMovieList = movieList;
    }

    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        int layoutIdForGridItem = R.layout.activity_movie_poster_item;
        LayoutInflater inflater = LayoutInflater.from(ctx);

        View view = inflater.inflate(layoutIdForGridItem, parent, false);
        PosterViewHolder posterViewHolder = new PosterViewHolder(view);

        return posterViewHolder;
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
