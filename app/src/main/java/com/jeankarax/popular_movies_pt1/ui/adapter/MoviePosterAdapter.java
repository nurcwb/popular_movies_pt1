package com.jeankarax.popular_movies_pt1.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
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
    private Context mCtx;

    public void setmMovieList(List<MovieData> mMovieList) {
        this.mMovieList = mMovieList;
        notifyDataSetChanged();
    }

    private final MoviePosterAdapterOnClickHandler mClickHandler;

    public interface MoviePosterAdapterOnClickHandler{
        void onClick(MovieData movieData);
    }

    public MoviePosterAdapter(MoviePosterAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mCtx = parent.getContext();
        int layoutIdForGridItem = R.layout.activity_movie_poster_item;
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(layoutIdForGridItem, parent, false);
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        lp.height = parent.getMeasuredHeight() / 2;
        view.setLayoutParams(lp);
        PosterViewHolder posterViewHolder = new PosterViewHolder(view);

        return posterViewHolder;
    }

    @Override
    public void onBindViewHolder(PosterViewHolder holder, int position) {
        String mImageUrl = "http://image.tmdb.org/t/p/w500/";
        Uri movieUri = Uri.parse(mImageUrl).buildUpon().appendEncodedPath(mMovieList.get(position)
                .getPoster_path()).build();
        Picasso.with(mCtx).load(movieUri).into(holder.ivMoviePoster);
        holder.ivMoviePoster.setAdjustViewBounds(true);
    }

    @Override
    public int getItemCount() {
        if(null == mMovieList) {
            return 0;
        }else{
            return mMovieList.size();
        }
    }

    public class PosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView ivMoviePoster;

        public PosterViewHolder(View itemView) {
            super(itemView);
            ivMoviePoster = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
            ivMoviePoster.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            int adapterPosition = getAdapterPosition();
            MovieData mMovieData = mMovieList.get(adapterPosition);
            mClickHandler.onClick(mMovieData);
        }
    }

}
