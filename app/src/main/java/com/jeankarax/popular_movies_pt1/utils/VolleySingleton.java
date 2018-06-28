package com.jeankarax.popular_movies_pt1.utils;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Jean Karax
 */

public class VolleySingleton {
    private static VolleySingleton ourInstance;
    private RequestQueue mRequestQueue;

    public static synchronized VolleySingleton getInstance(Context ctx) {
        if(null == ourInstance){
            ourInstance = new VolleySingleton(ctx);
        }
        return ourInstance;
    }

    private VolleySingleton(Context ctx) {
        mRequestQueue = Volley.newRequestQueue(ctx);
    }

    public RequestQueue getmRequestQueue(){
        return this.mRequestQueue;
    }
}
