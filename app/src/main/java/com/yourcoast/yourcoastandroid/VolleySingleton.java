package com.yourcoast.yourcoastandroid;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private VolleySingleton (Context context){
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    //returns request queue
    private RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    //returns instance of class
    public static synchronized VolleySingleton getInstance(Context context){
        if(mInstance==null){
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    //add each of the requests to the request queue
    public<T> void addToRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }







}
