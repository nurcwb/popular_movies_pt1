package com.jeankarax.popular_movies_pt1.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jean Karax
 */

public class MovieDataResponse implements Serializable{

    public MovieDataResponse() {
    }

    public List<MovieData> getResults() {
        return results;
    }

    public void setResults(List<MovieData> results) {
        this.results = results;
    }

    private List<MovieData> results;

}
